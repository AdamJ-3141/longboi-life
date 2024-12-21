package com.spacecomplexity.longboilife.game.sound;

import com.badlogic.gdx.audio.Music;

public class MusicHandler {

    public static GameMusic currentSong;
    private static GameMusic[] playlist;
    private static int currentSongIndex = 0;

    public MusicHandler() {
        currentSong = null;
        playlist = new GameMusic[GameMusic.values().length];
        System.arraycopy(GameMusic.values(), 0, playlist, 0, GameMusic.values().length);
    }

    public void nextSong() {
        currentSongIndex = (currentSongIndex + 1) % playlist.length;
        currentSong = playlist[currentSongIndex];
        playMusic();
    }

    public void playMusic() {
        currentSong.play();
        currentSong.music.setOnCompletionListener(music -> {
            nextSong();
        });
    }

    public void stopMusic() {
        currentSong.stop();
    }

    public void dispose() {
        for (GameMusic music : playlist) {
            music.dispose();
        }
    }
}
