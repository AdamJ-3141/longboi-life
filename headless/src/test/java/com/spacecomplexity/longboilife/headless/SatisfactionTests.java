package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spacecomplexity.longboilife.game.utils.GameUtils;
import com.spacecomplexity.longboilife.game.world.World;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SatisfactionTests extends AbstractHeadlessGdxTest{

    private World empty_world;
    private float satisfactionSum;
    private HashMap<Building, Float> accomSatisfactions;

    public static void assertClose(float expected, float actual, float delta, String message) {
        assertTrue(actual < expected + delta && actual > expected - delta, message);
    }

    @BeforeEach
    public void loadWorld() {
        try {
            gameState.reset();
            empty_world = new World(Gdx.files.internal("test_maps/map_allGrass.json"));
            System.out.println("Loaded empty world");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void emptyMap() {
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        accomSatisfactions = gameState.accomSatisfaction;
        assertEquals(0, accomSatisfactions.size(), "No accommodation -> No accommodation satisfaction");
        assertEquals(0, satisfactionSum, "No accommodation -> no satisfaction.");
        assertEquals(0, gameState.targetSatisfaction, "No accommodation -> no satisfaction.");
    }

    @Test
    public void oneHalls() {
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));

        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        accomSatisfactions = gameState.accomSatisfaction;
        assertEquals(1, accomSatisfactions.size(), "One accommodation -> One entry to HashMap");
        assertEquals(0, satisfactionSum, "Accommodation has no amenities -> No satisfaction");
        assertEquals(0, gameState.targetSatisfaction, "Accommodation has no amenities -> No satisfaction");
    }
    @Test
    public void hallsNearLibrary() {
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(4,0));
        empty_world.build(BuildingType.ROAD, new Vector2Int(3,0));

        // Distance = 1
        // Quality = 1
        // AccomQuality = (2 * arctan(0.9) / pi) + 0.5
        // Category Satisfaction = 1 * 1 * 0.966
        // No Recreation / Food
        // (0.966 + 0 + 0) / 3 = 0.322
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        assertClose(0.322f, satisfactionSum, 0.001f,"Library should generate 0.3 satisfaction");
        assertClose(0.322f, gameState.targetSatisfaction, 0.001f,"Library should generate 0.3 satisfaction");
    }
}
