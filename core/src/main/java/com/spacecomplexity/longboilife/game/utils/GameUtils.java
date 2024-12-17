package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
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
     * Satisfaction score is calculated using {@link Constants#satisfactoryDistance}.
     * For each accommodation building a modifier is created which is updated based on the positioning of other
     * buildings with on {@link Constants#satisfactoryDistance}. After all modifiers are calculated the worst one is
     * chosen to act as the satisfaction modifier, this then updates the satisfaction velocity.
     *
     * @param world the world reference for buildings.
     *
     * @return the sum of the satisfactions of each accommodation building.
     */
    public static float updateSatisfactionScore(World world) {

        GraphNode[] buildingGraph = generateGraph(world);
        HashMap<Building, Float> accomSatisfactionScore = new HashMap<>();
        for (GraphNode node : buildingGraph) {
            if (node.getBuildingRef().getType().getCategory() == BuildingCategory.ACCOMMODATION) {
                HashMap<BuildingCategory, Float> categoryScore = new HashMap<>();
                for (BuildingCategory cat : Constants.satisfactoryDistance.keySet()) {
                    categoryScore.put(cat, 0f);
                }
                for (GraphNode connected : node.getConnectedNodes().keySet()) {
                    if (connected.getBuildingRef().getType().getCategory() != BuildingCategory.ACCOMMODATION) {
                        int dist = node.getConnectedNodes().get(connected);
                        BuildingType type = connected.getBuildingRef().getType();
                        BuildingCategory category = type.getCategory();
                        float goodDist = Constants.satisfactoryDistance.get(category);
                        float badDist = Constants.ignoreDistance.get(category);
                        float distSatisfaction = (float) Math.min(1, Math.exp(goodDist * (goodDist - dist)/badDist));
                        Optional<Float> max = Stream.of(BuildingType.getBuildingsOfType(category))
                                                    .map(BuildingType::getCost)
                                                    .max(Float::compare);
                        float maxCost = max.orElse(1f);
                        float qualitySatisfaction = type.getCost() / maxCost;
                        float buildingSatisfaction = qualitySatisfaction * distSatisfaction;
                        categoryScore.compute(category,
                            (k, v) -> MathUtils.clamp(v + buildingSatisfaction, 0, 1));
                    }
                }
                float accomPrice = node.getBuildingRef().getType().getCost();
                float avgAccomPrice = Stream.of(BuildingType.getBuildingsOfType(BuildingCategory.ACCOMMODATION))
                                            .map(BuildingType::getCost)
                                            .reduce(0f, Float::sum)
                                            / BuildingType.getBuildingsOfType(BuildingCategory.ACCOMMODATION).length;
                float accomSatisfactionMult = (float) (2 * Math.atan(accomPrice / avgAccomPrice) / Math.PI) + 0.5f;
                accomSatisfactionScore.put(node.getBuildingRef(),
                    Math.min(1f, (categoryScore.values()
                        .stream().reduce(0f, Float::sum) / categoryScore.size())
                        * accomSatisfactionMult));
            }
        }
        float newSatisfactionSum = accomSatisfactionScore.values().stream().reduce(0f, Float::sum);
        if (!accomSatisfactionScore.isEmpty()) {
            float newSatisfactionScore = newSatisfactionSum / accomSatisfactionScore.size();
            GameState gameState = GameState.getState();
            gameState.targetSatisfaction = newSatisfactionScore;
            gameState.accomSatisfaction = accomSatisfactionScore;
        }
        return newSatisfactionSum;
    }

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

    public static GraphNode[] generateGraph(World world) {
        Vector<Building> buildings = new Vector<>(world.getBuildings());
        buildings.removeIf(b -> b.getType() == BuildingType.ROAD);
        GraphNode[] nodes = new GraphNode[buildings.size()];
        for (int i = 0; i < buildings.size(); i++) {
            Building b = buildings.get(i);
            nodes[i] = new GraphNode(b);
        }
        for (int i = 0; i < nodes.length; i++) {
            GraphNode startNode = nodes[i];
            for (int j = i + 1; j < nodes.length; j++) {
                GraphNode endNode = nodes[j];
                int dist = world.getBuildingDistance(startNode.getBuildingRef(), endNode.getBuildingRef());
                if (dist != Integer.MAX_VALUE) {
                    startNode.connectNode(endNode, dist);
                }
            }
        }
        return nodes;
    }
}
