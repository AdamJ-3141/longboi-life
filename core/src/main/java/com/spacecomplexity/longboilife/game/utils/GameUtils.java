package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
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
     */
    public static void updateSatisfactionScore(World world) {

//        Vector<Building> buildings = world.getBuildings();
//
//        // Map containing buildings split into categories
//        HashMap<BuildingCategory, Vector<Building>> categorisedBuildings = new HashMap<>();
//
//        // Get categories of buildings which will affect satisfaction score
//        Set<BuildingCategory> searchBuildingCategories = new HashSet<>(Constants.satisfactoryDistance.keySet());
//        // Temporarily add accommodation category so that accommodation buildings will be split out of the main building array
//        searchBuildingCategories.add(BuildingCategory.ACCOMMODATION);
//        // Initialise empty vectors
//        for (BuildingCategory category : searchBuildingCategories) {
//            categorisedBuildings.put(category, new Vector<>());
//        }
//        // Split buildings into categories
//        for (Building building : buildings) {
//            BuildingCategory category = building.getType().getCategory();
//            if (searchBuildingCategories.contains(category)) {
//                categorisedBuildings.get(category).add(building);
//            }
//        }
//        // Remove the accommodation category
//        searchBuildingCategories.remove(BuildingCategory.ACCOMMODATION);
//
//        // Check if any categories are empty, hence missing required buildings
//        boolean emptyCategory = false;
//        for (BuildingCategory category : searchBuildingCategories) {
//            if (categorisedBuildings.get(category).isEmpty()) {
//                emptyCategory = true;
//            }
//        }
//
//        // If there is are not required buildings this will be the default satisfaction modifier
//        float satisfactionModifier = -2000f;
//
//        if (!emptyCategory) {
//            // Get the maximum distance if items were placed on other sides of the map
//            float mapMax = new Vector2Int(world.getWidth(), world.getHeight()).mag();
//            satisfactionModifier = Float.MAX_VALUE;
//
//            // For every accommodation building
//            for (Building accomidationBuilding : categorisedBuildings.get(BuildingCategory.ACCOMMODATION)) {
//                float modifier = 0;
//
//                // Go through each other category of building
//                for (BuildingCategory category : searchBuildingCategories) {
//                    float closest = mapMax;
//                    // Find the closest of this category of building
//                    for (Building building : categorisedBuildings.get(category)) {
//                        float distance = (accomidationBuilding.getPosition().subtract(building.getPosition())).mag();
//                        if (distance < closest) {
//                            closest = distance;
//                        }
//                    }
//
//                    // Update the modifier by the constants defined with the satisfactory distance from the category
//                    modifier += Constants.satisfactoryDistance.get(category) - closest;
//
//                }
//
//                // Set the overall satisfaction modifier to the worst modifier from all accommodations
//                satisfactionModifier = Math.min(satisfactionModifier, modifier);
//            }
//        }

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
                        Optional<Float> max = Stream.of(BuildingType.getBuildingsOfType(category)).map(BuildingType::getCost).max(Float::compare);
                        float maxCost = max.orElse(1f);
                        float qualitySatisfaction = type.getCost() / maxCost;
                        float buildingSatisfaction = qualitySatisfaction * distSatisfaction;
                        categoryScore.compute(category, (k, v) -> Math.clamp(v + buildingSatisfaction, 0, 1));
                    }
                }
                accomSatisfactionScore.put(node.getBuildingRef(), categoryScore.values().stream().reduce(0f, Float::sum)/ categoryScore.size());
            }
        }
        float newSatisfactionSum = accomSatisfactionScore.values().stream().reduce(0f, Float::sum);
        if (!accomSatisfactionScore.isEmpty()) {
            float newSatisfactionScore = newSatisfactionSum / accomSatisfactionScore.size();
            GameState gameState = GameState.getState();

            float currentSatisfactionScore = gameState.satisfactionScore;
            gameState.satisfactionChangePositive = Math.signum(newSatisfactionScore - currentSatisfactionScore) >= 0;
            gameState.satisfactionScoreDelta = Math.max((newSatisfactionScore - currentSatisfactionScore) / 500f, 0.0001f);
            if (gameState.satisfactionChangePositive) {
                gameState.satisfactionScore = Math.min(newSatisfactionScore, currentSatisfactionScore + gameState.satisfactionScoreDelta);
            } else {
                gameState.satisfactionScore = Math.max(newSatisfactionScore, currentSatisfactionScore + gameState.satisfactionScoreDelta);
            }
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
