package com.spacecomplexity.longboilife.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicHandler {

    public static MusicPlaylist currentSong;
    private final int FADE_DELTA_TIME = 10;

    public MusicHandler() {
        currentSong = null;
    }

    public void nextSong(float deltaTime) {
        if (!currentSong.music.isPlaying()) {

        }
    }

    public void playMusic(float fadeTimeMillis) {
        currentSong.music.setVolume(0);
        currentSong.music.setLooping(true);
        currentSong.music.play();
    }

    public void stopMusic() {

    }

    public void dispose() {

    }
}
