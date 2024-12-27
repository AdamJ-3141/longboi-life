package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexity.longboilife.game.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementTests extends AbstractHeadlessGdxTest {
    private GameState gameState;

    @BeforeEach
    public void getGameState() {
        gameState = GameState.getState();
    }

    /**
     * Tests if the firstBuilding achievement is completed when a building is built
     */
    @Test
    public void firstBuilding() {
        assertFalse(AchievementType.FIRSTBUILDING.isAchieved());
        gameState.changeBuildingCount(BuildingType.GREGGS, 1);
        assertTrue(AchievementType.FIRSTBUILDING.isAchieved());
    }

    /**
     * Tests if the tenBuilding achievement is completed when 10 buildings are built
     */
    @Test
    public void tenBuilding() {
        assertFalse(AchievementType.TENBUILDING.isAchieved());
        gameState.changeBuildingCount(BuildingType.GREGGS, 10);
        assertTrue(AchievementType.TENBUILDING.isAchieved());
    }

    /**
     * Tests if the fiftyBuilding achievement is completed when 50 buildings are built
     */
    @Test
    public void fiftyBuilding() {
        assertFalse(AchievementType.FIFTYBUILDING.isAchieved());
        gameState.changeBuildingCount(BuildingType.GREGGS, 50);
        assertTrue(AchievementType.FIFTYBUILDING.isAchieved());
    }

    /**
     * Tests if the fullMap achievement is completed when the map has no buildable tiles remaining
     */
    @Test
    public void fullMap() {
        assertFalse(AchievementType.FULLMAP.isAchieved());
        World world = gameState.gameWorld;
        int worldWidth = world.getWidth();
        int worldHeight = world.getHeight();

        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                try {
                    world.build(BuildingType.ROAD, new Vector2Int(i, j));
                } catch (Exception ignored) {}
            }
        }

        assertTrue(AchievementType.FULLMAP.isAchieved());
    }

    /**
     * Tests if the fiveGreggs achievement is completed when 5 Greggs are built
     */
    @Test
    public void fiveGreggs() {
        assertFalse(AchievementType.FIVEGREGGS.isAchieved());
        gameState.changeBuildingCount(BuildingType.GREGGS, 5);
        assertTrue(AchievementType.FIVEGREGGS.isAchieved());
    }

    /**
     * Tests if the threeHundredRoads achievement is completed when 300 roads are built
     */
    @Test
    public void threeHundredRoads() {
        assertFalse(AchievementType.THREEHUNDREDROAD.isAchieved());
        gameState.changeBuildingCount(BuildingType.ROAD, 300);
        assertTrue(AchievementType.THREEHUNDREDROAD.isAchieved());
    }

    /**
     * Tests if the oneOfEachBuilding achievement is completed when one of each building is built
     */
    @Test
    public void oneOfEachBuilding() {
        assertFalse(AchievementType.ONEOFEACH.isAchieved());
        for (BuildingType buildingType : BuildingType.values()) {
            gameState.changeBuildingCount(buildingType, 1);
        }
        assertTrue(AchievementType.ONEOFEACH.isAchieved());
    }

    /**
     * Tests if the fiveOfEachBuilding achievement is completed when 5 of each building is built
     */
    @Test
    public void fiveOfEachBuilding() {
        assertFalse(AchievementType.FIVEOFEACH.isAchieved());
        for (BuildingType buildingType : BuildingType.values()) {
            gameState.changeBuildingCount(buildingType, 5);
        }
        assertTrue(AchievementType.FIVEOFEACH.isAchieved());
    }

    /**
     * Tests if the millionaire achievement is completed when the player reaches <1,000,000 money
     */
    @Test
    public void millionaire() {
        assertFalse(AchievementType.MILLIONAIRE.isAchieved());
        gameState.money = 1000000;
        assertTrue(AchievementType.MILLIONAIRE.isAchieved());
    }

    /**
     * Tests if the perfection achievement is completed when the satisfaction score reaches 100
     */
    @Test
    public void perfection() {
        assertFalse(AchievementType.PERFECTION.isAchieved());
        gameState.targetSatisfaction = 100;
        assertTrue(AchievementType.PERFECTION.isAchieved());
    }
}
