package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

import com.spacecomplexity.longboilife.game.globals.GameState;

public abstract class AbstractHeadlessGdxTest {

    public GameState gameState;

    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }
}
