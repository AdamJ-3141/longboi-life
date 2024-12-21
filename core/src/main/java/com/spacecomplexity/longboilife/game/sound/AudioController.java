package com.spacecomplexity.longboilife.game.sound;

import com.badlogic.gdx.audio.Music;

public class AudioController {

    private MusicHandler musicHandler;
    public AudioController() {
        musicHandler = new MusicHandler();
    }

    public void playSound(SoundEffect sound) {
        sound.play();
    }

    public void startMusic() {
        musicHandler.start();
    }

}
