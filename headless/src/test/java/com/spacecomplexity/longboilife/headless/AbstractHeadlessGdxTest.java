package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.GL20;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

import com.spacecomplexity.longboilife.game.globals.GameState;

public abstract class AbstractHeadlessGdxTest {

    public GameState gameState = GameState.getState();
    protected HeadlessMain game;

    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        Gdx.graphics = new MockGraphics();
        game = HeadlessLauncher.initialize();
        game.switchScreen(HeadlessMain.ScreenType.GAME);

        // Manually trigger `show()` if not automatically called
        if (game.getScreen() instanceof HeadlessGameScreen) {
            game.getScreen().show();
        }
    }
}
