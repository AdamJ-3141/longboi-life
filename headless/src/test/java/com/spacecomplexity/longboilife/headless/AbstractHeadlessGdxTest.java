package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

import com.spacecomplexity.longboilife.game.globals.GameState;

import java.util.concurrent.TimeoutException;

public abstract class AbstractHeadlessGdxTest {

    public GameState gameState = GameState.getState();
    protected HeadlessMain game;

    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        Gdx.graphics = new MockGraphics();
        game = HeadlessLauncher.initialize();

        // Manually trigger `show()` if not automatically called
        if (game.getScreen() instanceof HeadlessGameScreen) {
            game.getScreen().show();
        }
    }

    public void waitForLoad(float seconds) throws InterruptedException, TimeoutException {
        int delta = 10;
        for (int i = 0; i < seconds * 1000; ) {
            i += delta;
            Thread.sleep(delta);
            if (gameState.isLoaded) {
                return;
            }
        }
        throw new TimeoutException();
    }
}
