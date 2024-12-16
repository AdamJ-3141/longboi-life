package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import  static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class AssetTests extends AbstractHeadlessGdxTest{
    // have to use direct file addresses as original group input data into methods and classes directly instead of using variables
    @Test
    public void buildingAsset() {
        assertTrue(Gdx.files.internal("buildings/greggs.png").exists(),
            "Greggs texture exists");
        assertTrue(Gdx.files.internal("buildings/nandos.png").exists(),
            "Nandos texture exists");
        assertTrue(Gdx.files.internal("buildings/library.png").exists(),
            "Library texture exists");
        assertTrue(Gdx.files.internal("buildings/gym.png").exists(),
            "Gym texture exists");
        assertTrue(Gdx.files.internal("buildings/halls.png").exists(),
            "Halls texture exists");
    }
}
