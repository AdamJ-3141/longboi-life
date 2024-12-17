package com.spacecomplexity.longboilife.game.globals;

import com.spacecomplexity.longboilife.game.building.BuildingCategory;

import java.util.HashMap;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.Color;

/**
 * Class contain the constants used throughout the game.
 */
public class Constants {
    /**
     * The size of tiles (in px).
     */
    public static final int TILE_SIZE = 8;

    /**
     * The height of the window at scaling level 1.
     */
    public static final int SCALING_1_HEIGHT = 1080;

    /**
     * Map of the UI scaling at the specified window height.
     */
    public static final TreeMap<Integer, Float> UI_SCALING_MAP = new TreeMap<>() {{
        put(0, 1f);
        put(720, 1.5f);
        put(1440, 2f);
        put(2160, 2.5f);
    }};

    /**
     * Minimum camera zoom level.
     */
    public static final float MIN_ZOOM = 0.05f;

    /**
     * Maximum camera zoom level.
     */
    public static final float MAX_ZOOM = 0.5f;

    /**
     * The proportion of money the user will get back form selling the building.
     */
    public static float sellCostRecovery = 0.5f;

    /**
     * The proportion of money the user will need in order to move the building.
     */
    public static float moveCostRecovery = 0.25f;

    /**
     * Which category of buildings will not be deselected when built.
     */
    public static BuildingCategory[] dontRemoveSelection = new BuildingCategory[]{
        BuildingCategory.PATHWAY
    };

    /**
     * Which colour each building category will be displayed as on the sidebar.
     */
    public static HashMap<BuildingCategory, Color> categoryColours = new HashMap<>() {{
        put(BuildingCategory.PATHWAY, Color.WHITE);
        put(BuildingCategory.FOOD, Color.YELLOW);
        put(BuildingCategory.ACCOMMODATION, Color.RED);
        put(BuildingCategory.EDUCATIONAL, Color.CYAN);
        put(BuildingCategory.RECREATIONAL, Color.GREEN);
    }};

    /**
     * The satisfactory distances from accommodation to a specific category of building
     * <p>
     * Used for calculating satisfaction score
     */
    public static HashMap<BuildingCategory, Float> satisfactoryDistance = new HashMap<>() {{
        put(BuildingCategory.FOOD, 5f);
        put(BuildingCategory.RECREATIONAL, 10f);
        put(BuildingCategory.EDUCATIONAL, 5f);
    }};

    /**
     * The distances from accommodation to a specific category of building
     * where the accommodation will ignore the building in satisfaction calculations.
     * <p>
     * Used for calculating satisfaction score
     */
    public static HashMap<BuildingCategory, Float> ignoreDistance = new HashMap<>() {{
        put(BuildingCategory.FOOD, 25f);
        put(BuildingCategory.RECREATIONAL, 30f);
        put(BuildingCategory.EDUCATIONAL, 30f);
    }};
}
