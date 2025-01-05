package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.utils.AchievementManager;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexity.longboilife.game.world.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertFalse(AchievementType.FIRSTBUILDING.isAchieved(),
            "First building achievement expected to not be achieved as freshly created game");
        gameState.changeBuildingCount(BuildingType.GREGGS, 1);
        assertTrue(AchievementType.FIRSTBUILDING.isAchieved(),
            "First building achievement expected to be achieved as one building has been built");
    }

    /**
     * Tests if the tenBuilding achievement is completed when 10 buildings are built
     */
    @Test
    public void tenBuilding() {
        assertFalse(AchievementType.TENBUILDING.isAchieved(),
            "Ten building achievement expected to not be achieved as freshly created game");
        gameState.changeBuildingCount(BuildingType.GREGGS, 10);
        assertTrue(AchievementType.TENBUILDING.isAchieved(),
            "Ten building achievement expected to be achieved as 10 buildings have been built");
    }

    /**
     * Tests if the fiftyBuilding achievement is completed when 50 buildings are built
     */
    @Test
    public void fiftyBuilding() {
        assertFalse(AchievementType.FIFTYBUILDING.isAchieved(),
            "Fifty building achievement expected to not be achieved as freshly created game");
        gameState.changeBuildingCount(BuildingType.GREGGS, 50);
        assertTrue(AchievementType.FIFTYBUILDING.isAchieved(),
            "Fifty building achievement expected to be achieved as 50 buildings have been built");
    }

    /**
     * Tests if the fullMap achievement is completed when the map has no buildable tiles remaining
     */
    @Test
    public void fullMap() {
        assertFalse(AchievementType.FULLMAP.isAchieved(),
            "Full map achievement expected to not be achieved as freshly created game");

        World world = gameState.gameWorld;
        int worldWidth = world.getWidth();
        int worldHeight = world.getHeight();

        // fills world with roads
        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                try {
                    world.build(BuildingType.ROAD, new Vector2Int(i, j));
                } catch (Exception ignored) {}
            }
        }

        assertTrue(AchievementType.FULLMAP.isAchieved(),
            "Full map achievement expected to be achieved as the map no longer has any placeable tiles");
    }

    /**
     * Tests if the fiveGreggs achievement is completed when 5 Greggs are built
     */
    @Test
    public void fiveGreggs() {
        assertFalse(AchievementType.FIVEGREGGS.isAchieved(),
            "Five greggs achievement expected to not be achieved as freshly created game");
        gameState.changeBuildingCount(BuildingType.GREGGS, 5);
        assertTrue(AchievementType.FIVEGREGGS.isAchieved(),
            "Five greggs achievement expected to be achieved as 5 Greggs have been built");
    }

    /**
     * Tests if the threeHundredRoads achievement is completed when 300 roads are built
     */
    @Test
    public void threeHundredRoads() {
        assertFalse(AchievementType.THREEHUNDREDROAD.isAchieved(),
            "Three hundred roads achievement expected to not be achieved as freshly created game");
        gameState.changeBuildingCount(BuildingType.ROAD, 300);
        assertTrue(AchievementType.THREEHUNDREDROAD.isAchieved(),
            "Three hundred roads achievement expected to be achieved as 300 Roads have been built");
    }

    /**
     * Tests if the oneOfEachBuilding achievement is completed when one of each building is built
     */
    @Test
    public void oneOfEachBuilding() {
        assertFalse(AchievementType.ONEOFEACH.isAchieved(),
            "One-of-each building achievement expected to not be achieved as freshly created game");
        for (BuildingType buildingType : BuildingType.values()) {
            gameState.changeBuildingCount(buildingType, 1);
        }
        assertTrue(AchievementType.ONEOFEACH.isAchieved(),
            "One-of-each building achievement expected to be achieved as one of every building have been built");
    }

    /**
     * Tests if the fiveOfEachBuilding achievement is completed when 5 of each building is built
     */
    @Test
    public void fiveOfEachBuilding() {
        assertFalse(AchievementType.FIVEOFEACH.isAchieved(),
            "Five-of-each building achievement expected to not be achieved as freshly created game");
        for (BuildingType buildingType : BuildingType.values()) {
            gameState.changeBuildingCount(buildingType, 5);
        }
        assertTrue(AchievementType.FIVEOFEACH.isAchieved(),
            "Five-of-each building achievement expected to be achieved as 5 of each building have been built");
    }

    /**
     * Tests if the millionaire achievement is completed when the player reaches <1,000,000 money
     */
    @Test
    public void millionaire() {
        assertFalse(AchievementType.MILLIONAIRE.isAchieved(),
            "Millionaire achievement expected to not be achieved as freshly created game");
        gameState.money = 1000000;
        assertTrue(AchievementType.MILLIONAIRE.isAchieved(),
            "Millionaire achievement expected to be achieved as 1000000 money has been reached");
        gameState.money = 1000001;
        assertTrue(AchievementType.MILLIONAIRE.isAchieved(),
            "Millionaire achievement expected to be achieved as more than 1000000 money has been reached");
    }

    /**
     * Tests if the perfection achievement is completed when the satisfaction score reaches 100
     */
    @Test
    public void perfection() {
        assertFalse(AchievementType.PERFECTION.isAchieved(),
            "Perfection achievement expected to not be achieved as freshly created game");
        gameState.targetSatisfaction = 100;
        assertTrue(AchievementType.PERFECTION.isAchieved(),
            "Perfection achievement expected to be achieved as 100 satisfaction has been reached");
    }

    /**
     * Causes the firstBuilding achievement to be complete
     */
    public void triggerFirstAchievement() {
        gameState.changeBuildingCount(BuildingType.GREGGS, 1);
        AchievementManager.checkAchievements();
    }

    /**
     * Tests if the possibleAchievements and completedAchievement arrays are updated correctly
     */
    @Test
    public void arrayAchievements() {
        assertEquals(0, AchievementManager.getCompleteAchievements().size(),
            "Completed achievements should be empty at creation");
        assertEquals(AchievementType.values().length, AchievementManager.getPossibleAchievements().size(),
            "Possible achievements should be full at creation");

        AchievementManager.checkAchievements();

        assertEquals(0, AchievementManager.getCompleteAchievements().size(),
            "Completed achievements should be empty as no achievement conditions met");
        assertEquals(AchievementType.values().length, AchievementManager.getPossibleAchievements().size(),
            "Possible achievements should be full as no achievement conditions met");

        triggerFirstAchievement();

        assertEquals(1, AchievementManager.getCompleteAchievements().size(),
            "Completed Achievements should have a length of 1 as one achievement condition has been met");
        assertEquals(AchievementType.values().length-1, AchievementManager.getPossibleAchievements().size(),
            "Possible Achievements should be decremented by 1 as one achievement condition has been met");
    }

    /**
     * Tests if the current achievement global variable is updated correctly
     */
    @Test
    public void currentAchievementUpdated() {
        assertNull(gameState.currentAchievement, "Current achievement should be null initially");
        triggerFirstAchievement();
        assertEquals(AchievementType.FIRSTBUILDING, gameState.currentAchievement,
            "Current achievement should be first building as that achievement has been reached");

        AchievementManager.updateAchievements();

        assertEquals(AchievementType.FIRSTBUILDING, gameState.currentAchievement,
            "Current achievement should still be first building as no in-game time as passed");

        long currentTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        MainTimer.getTimerManager().getTimer().setTimer(currentTime - (AchievementManager.achievementLiveTime * 1000) - 1);
        AchievementManager.updateAchievements();

        assertNull(gameState.currentAchievement,
            "Current achievement should be null as time for achievement to live has expired");
    }
}
