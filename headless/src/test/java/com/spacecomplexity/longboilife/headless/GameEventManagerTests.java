package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spacecomplexity.longboilife.game.gameevent.GameEventManager;

public class GameEventManagerTests extends AbstractHeadlessGdxTest {
    public GameEventManager manager;

    @BeforeEach
    public void getManager() {
        manager = GameEventManager.getGameEventManager();
    }

    @Test
    public void tryForGameEventTimeNotPassedTest() {
        assertFalse(manager.tryForGameEvent());
    }
}
