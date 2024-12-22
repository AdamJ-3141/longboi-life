package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class ParticleSpawner extends ParticleEffect {

    boolean scaled = false;

    public ParticleSpawner(FileHandle emitterFile, FileHandle imageFile, float x, float y) {
        super();
        load(emitterFile, imageFile);
        setPosition(x, y);
        start();
    }
}
