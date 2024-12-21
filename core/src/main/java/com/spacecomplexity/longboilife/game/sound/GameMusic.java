package com.spacecomplexity.longboilife.game.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public enum GameMusic {

    SONG_1("A New Day Begins", "One Man Symphony",
        Gdx.files.internal("audio/music/One Man Symphony - A New Day Begins.mp3"),
        Gdx.files.internal("audio/music/cover.png")),
    SONG_2("A New Day's Hurry", "One Man Symphony",
        Gdx.files.internal("audio/music/One Man Symphony - A New Day's Hurry.mp3"),
        Gdx.files.internal("audio/music/cover.png")),
    ;

    public final String title;
    public final Music music;
    public final FileHandle coverFile;
    public final String composer;

    GameMusic(String title, String composer, FileHandle audioFile, FileHandle coverFile) {
        this.title = title;
        this.music = Gdx.audio.newMusic(audioFile);
        this.coverFile = coverFile;
        this.composer = composer;
    }

    public void play() {
        music.play();
    }

    public void stop() {
        music.stop();
    }

    public void setVolume(float volume) {
        music.setVolume(volume);
    }

    public void dispose() {
        music.dispose();
    }

}
