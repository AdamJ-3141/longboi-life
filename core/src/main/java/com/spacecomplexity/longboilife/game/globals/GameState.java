package com.spacecomplexity.longboilife.game.globals;

import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.world.World;

import java.util.HashMap;

/**
 * Singleton class to contain variables relating to state/config of the game.
 */
public class GameState {
    private static final GameState gameState = new GameState();

    /**
     * The current scale factor of game for rendering.
     */
    public float scaleFactor = 1;

    /**
     * The current scale factor of the UI.
     */
    public float uiScaleFactor = 1;

    /**
     * Camera speed whilst controlling with keyboard.
     */
    public float cameraSpeed;

    /**
     * Camera zooming speed whilst controlling with keyboard.
     */
    public float cameraKeyZoomSpeed;

    /**
     * Camera zooming speed whilst controlling with mouse/trackpad.
     */
    public float cameraScrollZoomSpeed;

    /**
     * Points the current game world object.
     */
    public World gameWorld;

    /**
     * The amount of money the player currently has.
     */
    public float money;

    /**
     * The current satisfaction score.
     */
    public float satisfactionScore;

    /**
     * The actual average satisfaction score that {@link GameState#satisfactionScore}
     * will tend towards.
     */
    public float targetSatisfaction;

    /**
     * A map from each accommodation building to its satisfaction rating.
     */
    public HashMap<Building, Float> accomSatisfaction;

    /**
     * The main total score of the current game.
     */
    public int totalScore;

    /**
     * The building selected to be placed.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public BuildingType placingBuilding;

    /**
     * The currently selected building on the map.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public Building selectedBuilding;

    /**
     * The currently selected building to be moved.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public Building movingBuilding;

    /**
     * Whether the game should deselect a building once placed.
     * <p>
     * Handled by {@link Keybindings#KEEP_PLACING}
     */
    public boolean continuousPlacingBuilding;

    /**
     * If the game is currently paused.
     */
    public boolean paused;

    /**
     * The current count of buildings.
     * <p>
     * This should be modified by {@link GameState#getBuildingCount(BuildingType)} and {@link GameState#changeBuildingCount(BuildingType, int)} not directly.
     * <p>
     * This is initialised in the constructor.
     */
    public HashMap<BuildingType, Integer> buildingsCount;

    /**
     * Helper function to get the number of a specified building.
     *
     * @param buildingType the building type to get.
     * @return the building count for this specific building.
     */
    public Integer getBuildingCount(BuildingType buildingType) {
        Integer count = buildingsCount.get(buildingType);
        // If this has not yet been set return 0
        if (count == null) {
            return 0;
        }

        return count;
    }

    /**
     * @return the total number of building in the game not including roads
     */
    public Integer getBuildingCount() {
        int count = 0;
        for (BuildingType buildingType : BuildingType.values()) {
            if (buildingType != BuildingType.ROAD)
                count += getBuildingCount(buildingType);
        }
        return count;
    }

    /**
     * Helper function to change the number of a specified building.
     *
     * @param buildingType the building type to change.
     * @param change       the amount to change it by.
     */
    public void changeBuildingCount(BuildingType buildingType, int change) {
        int count = getBuildingCount(buildingType);
        buildingsCount.put(buildingType, count + change);
    }

    /**
     * The value that the satisfaction score changes by each second.
     */
    public float satisfactionScoreDelta = 0;

    /**
     * If the last satisfaction modifier calculated was positive.
     */
    public boolean satisfactionChangePositive = false;

    /**
     * If the game has ended
     */
    public boolean gameOver = false;

    /**
     * Get the singleton instance of the {@link GameState} class.
     *
     * @return The single {@link GameState} class.
     */
    public static GameState getState() {
        return gameState;
    }

    private GameState() {
        reset();
    }

    /**
     * Keep track of the currently rendered achievement null if no achievement
     */
    public AchievementType currentAchievement = null;

    /**
     * Reset all values to default.
     */
    public void reset() {
//        scaleFactor = 1;
//        uiScaleFactor = 1;
        cameraSpeed = 1400;
        cameraKeyZoomSpeed = 3;
        cameraScrollZoomSpeed = 32;
//        fullscreen = false;
        gameWorld = null;
        money = 50000;
        satisfactionScore = 0f;
        targetSatisfaction = 0f;
        accomSatisfaction = new HashMap<>();
        placingBuilding = null;
        continuousPlacingBuilding = false;
        selectedBuilding = null;
        movingBuilding = null;
        paused = false;
        buildingsCount = new HashMap<>();
        satisfactionScoreDelta = 0;
        satisfactionChangePositive = false;
        gameOver = false;
        currentAchievement = null;
    }
}
