package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * A class used to create a {@link ParticleEffect} more easily.
 */
public class ParticleSpawner extends ParticleEffect {

    boolean scaled = false;

    /**
     * Loads the required files and positions and initializes the ParticleEffect.
     * @param emitterFile points to the emitter file
     * @param imageFile points to the images directory
     * @param x position
     * @param y position
     */
    public ParticleSpawner(FileHandle emitterFile, FileHandle imageFile, float x, float y) {
        super();
        load(emitterFile, imageFile);
        setPosition(x, y);
        start();
    }
}
