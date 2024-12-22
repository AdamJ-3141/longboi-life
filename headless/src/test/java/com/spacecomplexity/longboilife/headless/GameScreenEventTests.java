package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

public class GameScreenEventTests extends AbstractHeadlessGdxTest {
    private GameState gameState;

    @BeforeEach
    public void getGameState() {
        gameState = GameState.getState();

        try {
            waitForLoad(10);
        } catch (InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBuildEvent() {

        // Set up the game state for building
        gameState.placingBuilding = BuildingType.ROAD;
        gameState.money = 100; // Ensure sufficient money

        // Simulate the BUILD event
        EventHandler.getEventHandler().callEvent(EventHandler.Event.BUILD);

        // Verify the building was placed
        assertNotNull(gameState.gameWorld.getTile(new Vector2Int(0, 0)).getBuildingRef(), "Building should be placed on the world grid");
        assertTrue(gameState.money < 100, "Money should be deducted after building");
    }
}
