package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainCamera;
import com.spacecomplexity.longboilife.game.world.World;

import java.util.*;
import java.util.stream.Stream;

/**
 * A class used for game utilities.
 */
public class GameUtils {

    /**
     * Get the current position of the mouse relative to the world grid.
     *
     * @param world the world reference for size.
     * @return the grid index at the current mouse position.
     */
    public static Vector2Int getMouseOnGrid(World world) {
        // Get the position of mouse in world coordinates
        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        MainCamera.camera().getCamera().unproject(mouse);

        // Divide these by the cell size (as the world starts at (0, 0))
        float cellSize = Constants.TILE_SIZE * GameState.getState().scaleFactor;
        return new Vector2Int(
            Math.max(0, Math.min((int) (mouse.x / cellSize), world.getWidth() - 1)),
            Math.max(0, Math.min((int) (mouse.y / cellSize), world.getHeight() - 1))
        );
    }

    /**
     * Calculate and set scaling factors using the window size.
     */
    public static void calculateScaling() {
        int screenHeight = Gdx.graphics.getHeight();

        // If height is 0 then the window is minimised so don't bother calculating as this could cause unintended behaviour with scaling at 0
        if (screenHeight == 0)
            return;

        // Calculate scale factor based on screen height linearly using constant
        GameState.getState().scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;
        // Calculate UI scale factor based on screen height using scaling map
        GameState.getState().uiScaleFactor = Constants.UI_SCALING_MAP.floorEntry(screenHeight).getValue();
    }

    /**
     * Update satisfaction score.
     * <p>
     * Satisfaction score is calculated using {@link Constants#satisfactoryDistance} and {@link Constants#ignoreDistance}.
     * A graph is created where each node is a non-pathway building and each edge is the distance along the pathways
     * using {@link World#getPathDistance(Vector2Int, Vector2Int)}.
     * <p>
     * Each accommodation generates its own satisfaction score, relating to the distance from other buildings and the
     * quality of those buildings, grouped by building category. The satisfaction score displayed on the screen is the
     * average of all accommodation satisfaction scores.
     * <p>
     * The sum of all accommodation satisfaction scores is added to the {@link GameState#totalScore} every 10 seconds.
     *
     * @param world the world reference for buildings.
     *
     * @return the sum of the satisfactions of each accommodation building.
     */
    public static float updateSatisfactionScore(World world) {

        GraphNode[] buildingGraph = generateGraph(world);
        HashMap<Building, AccomSatisfactionDetail> accomSatisfactionDetails = new HashMap<>();
        for (GraphNode node : buildingGraph) {
            // For each accommodation building node
            if (node.getBuildingRef().getType().getCategory() == BuildingCategory.ACCOMMODATION) {

                // Initialise the building category satisfactions for the node, each to zero.
                HashMap<BuildingCategory, Float> categoryScore = new HashMap<>();
                for (BuildingCategory cat : Constants.satisfactoryDistance.keySet()) {
                    categoryScore.put(cat, 0f);
                }

                // Checks through each connected non-accommodation building
                for (GraphNode connected : node.getConnectedNodes().keySet()) {
                    if (connected.getBuildingRef().getType().getCategory() != BuildingCategory.ACCOMMODATION) {

                        // Compares the distance between the nodes to the satisfactory distance and ignore distance
                        // of the building category.
                        int dist = node.getConnectedNodes().get(connected);
                        BuildingType type = connected.getBuildingRef().getType();
                        BuildingCategory category = type.getCategory();
                        float goodDist = Constants.satisfactoryDistance.get(category);
                        float badDist = Constants.ignoreDistance.get(category);

                        // Some maths to work out the "Distance Satisfaction" (closer = higher)
                        float distSatisfaction = (float) Math.min(1, Math.exp(goodDist * (goodDist - dist)/badDist));
                        if (dist >= badDist) {
                            distSatisfaction = 0f;
                        }

                        // Finds the highest cost BuildingType of the building category.
                        Optional<Float> max = Stream.of(BuildingType.getBuildingsOfType(category))
                                                    .map(BuildingType::getCost)
                                                    .max(Float::compare);
                        float maxCost = max.orElse(1f);

                        // Calculates "Quality Satisfaction" and overall "Building Satisfaction"
                        float qualitySatisfaction = type.getCost() / maxCost;
                        float buildingSatisfaction = qualitySatisfaction * distSatisfaction;

                        // Adds the building satisfaction to the current category score for that accommodation.
                        categoryScore.compute(category,
                            (k, v) -> MathUtils.clamp(((v != null) ? v : 0) + buildingSatisfaction, 0, 1));
                    }
                }
                // Calculates a multiplier relating to the relative "quality" (price) of the accommodation.
                float accomPrice = node.getBuildingRef().getType().getCost();
                float avgAccomPrice = Stream.of(BuildingType.getBuildingsOfType(BuildingCategory.ACCOMMODATION))
                                            .map(BuildingType::getCost)
                                            .reduce(0f, Float::sum)
                                            / BuildingType.getBuildingsOfType(BuildingCategory.ACCOMMODATION).length;

                // Multiplier is normalized to be closer to 1 for balancing.
                float accomSatisfactionMult = (float) (2 * Math.atan(accomPrice / avgAccomPrice) / Math.PI) + 0.5f;

                // Get specific modifier from GameState
                SatisfactionModifier localMod = GameState.getState().accomSatisfactionModifiers
                    .getOrDefault(node.getBuildingRef(),
                                  new SatisfactionModifier());

                SatisfactionModifier globalMod = GameState.getState().globalSatisfactionModifier;

                float totalAccomSatisfaction = MathUtils.clamp((categoryScore.values()
                    .stream().reduce(0f, Float::sum) / categoryScore.size())
                    * accomSatisfactionMult * localMod.relative * globalMod.relative + localMod.absolute + globalMod.absolute, 0f, 1f);

                AccomSatisfactionDetail details = new AccomSatisfactionDetail(accomSatisfactionMult, categoryScore, totalAccomSatisfaction, localMod);

                // The accommodation's satisfaction score is the average of the category scores, multiplied by the multiplier.
                accomSatisfactionDetails.put(node.getBuildingRef(), details);
            }
        }
        // Calculate the sum of all the accommodations' satisfactions.
        float newSatisfactionSum = accomSatisfactionDetails.values().stream()
            .map(d->d.totalSatisfaction)
            .reduce(0f, Float::sum);

        float newSatisfactionScore;
        GameState gameState = GameState.getState();
        // Check whether there are accommodation buildings placed.
        if (!accomSatisfactionDetails.isEmpty()) {
            newSatisfactionScore = newSatisfactionSum / accomSatisfactionDetails.size();

            // Check for changes in accommodation satisfaction.
            HashMap<Building, AccomSatisfactionDetail> oldSatisfactions = gameState.accomSatisfaction;
            gameState.accomSatisfaction = accomSatisfactionDetails;
            checkAccomSatisfaction(oldSatisfactions);
        } else {
            newSatisfactionScore = 0f;

            // Apply the absolute modifier only.
            newSatisfactionScore += gameState.globalSatisfactionModifier.absolute;
        }
        gameState.targetSatisfaction = MathUtils.clamp(newSatisfactionScore, 0f, 1f);

        // To be used to add onto the score.
        return newSatisfactionSum;
    }

    /**
     * Checks any differences between old and current accommodation satisfactions,
     * and then spawns particles in each case.
     * @param oldSatisfactions a HashMap from accommodation buildings to {@link AccomSatisfactionDetail}.
     */
    public static void checkAccomSatisfaction(HashMap<Building, AccomSatisfactionDetail> oldSatisfactions) {
        HashMap<Building, AccomSatisfactionDetail> newSatisfactions = GameState.getState().accomSatisfaction;

        // Checks each accommodation building in the current world for changes
        for (Building building : newSatisfactions.keySet()) {

            // If the building is new or the satisfaction has increased
            if (!oldSatisfactions.containsKey(building) ||
                newSatisfactions.get(building).totalSatisfaction > oldSatisfactions.get(building).totalSatisfaction) {

                // Spawns hearts at the correct position
                float cellSize = Constants.TILE_SIZE * GameState.getState().scaleFactor;
                EventHandler.getEventHandler().callEvent(EventHandler.Event.SPAWN_PARTICLE,
                        Gdx.files.internal("particles/effects/heart.p"),
                        Gdx.files.internal("particles/images"),
                        (building.getPosition().x + building.getType().getSize().x / 2) * cellSize,
                        (building.getPosition().y + building.getType().getSize().x / 2) * cellSize);

                AudioController.getInstance().playSound(SoundEffect.SATISFACTION_UP);
            }
            // If the satisfaction has decreased
            else if (newSatisfactions.get(building).totalSatisfaction < oldSatisfactions.get(building).totalSatisfaction) {

                // Spawn broken hearts at the correct position
                float cellSize = Constants.TILE_SIZE * GameState.getState().scaleFactor;
                EventHandler.getEventHandler().callEvent(EventHandler.Event.SPAWN_PARTICLE,
                    Gdx.files.internal("particles/effects/broken_heart.p"),
                    Gdx.files.internal("particles/images"),
                    (building.getPosition().x + building.getType().getSize().x / 2) * cellSize,
                    (building.getPosition().y + building.getType().getSize().x / 2) * cellSize);

                AudioController.getInstance().playSound(SoundEffect.SATISFACTION_DOWN);
            }
        }
    }

    /**
     * Called every screen render to move the {@link GameState#satisfactionScore}
     * towards the {@link GameState#targetSatisfaction}.
     * <p>
     * Updates {@link GameState#satisfactionChangePositive} so that the colour of the
     * satisfaction label is correct.
     */
    public static void updateVisibleSatisfactionScore() {
        GameState gameState = GameState.getState();
        float currentSatisfactionScore = gameState.satisfactionScore;
        float newSatisfactionScore = gameState.targetSatisfaction;
        gameState.satisfactionChangePositive = Math.signum(newSatisfactionScore - currentSatisfactionScore) >= 0;
        float satisfactionVel = (newSatisfactionScore - currentSatisfactionScore) / 500f;
        gameState.satisfactionScoreDelta = (Math.abs(satisfactionVel) < 0.001) ? Math.signum(newSatisfactionScore - currentSatisfactionScore) * 0.001f : satisfactionVel;
        if (gameState.satisfactionChangePositive) {
            gameState.satisfactionScore = MathUtils.clamp(Math.min(newSatisfactionScore, currentSatisfactionScore + gameState.satisfactionScoreDelta), 0f, 1f);
        } else {
            gameState.satisfactionScore = MathUtils.clamp(Math.max(newSatisfactionScore, currentSatisfactionScore + gameState.satisfactionScoreDelta), 0f, 1f);
        }
    }

    /**
     * Generates a graph of all the world's buildings and their distances.
     * @param world the current game world.
     * @return      the generated {@link GraphNode}
     */
    public static GraphNode[] generateGraph(World world) {

        // Sets all the non-pathway buildings to nodes.
        Vector<Building> buildings = new Vector<>(world.getBuildings());
        buildings.removeIf(b -> b.getType() == BuildingType.ROAD);
        GraphNode[] nodes = new GraphNode[buildings.size()];
        for (int i = 0; i < buildings.size(); i++) {
            Building b = buildings.get(i);
            nodes[i] = new GraphNode(b);
        }

        // Calculates the path distance between all the nodes and connects them if necessary.
        for (int i = 0; i < nodes.length; i++) {
            GraphNode startNode = nodes[i];
            for (int j = i + 1; j < nodes.length; j++) {
                GraphNode endNode = nodes[j];
                int dist = world.getBuildingDistance(startNode.getBuildingRef(), endNode.getBuildingRef());
                if (dist != -1) {
                    startNode.connectNode(endNode, dist);
                }
            }
        }
        return nodes;
    }

    /**
     * Gets the money that a building generates per money generation.
     * @param b the building queried, only {@link BuildingCategory#ACCOMMODATION}
     *          can generate money.
     * @return  the amount of money that building generates.
     */
    public static float getMoneyGenerated(Building b) {
        return getMoneyGenerated(b.getType());
    }


    /**
     * Gets the money that a building type generates per money generation.
     * @param type the building type queried, only {@link BuildingCategory#ACCOMMODATION}
     *             can generate money.
     * @return     the amount of money that building generates.
     */
    public static float getMoneyGenerated(BuildingType type) {
        if (type.getCategory() != BuildingCategory.ACCOMMODATION) { return 0; }
        else {
            // cbrt used to create slower return on investment for higher cost
            return Math.round(Math.cbrt(type.getCost())) * 100;
        }
    }
}
