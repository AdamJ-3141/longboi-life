package com.spacecomplexity.longboilife.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public enum GameMusic {

    GAME_1("A New Day Begins", "One Man Symphony",
        "audio/music/One Man Symphony - A New Day Begins.mp3",
        "audio/music/A_New_Days_Hurry_cover.png"),
    GAME_2("A New Day's Hurry", "One Man Symphony",
        "audio/music/One Man Symphony - A New Day's Hurry.mp3",
        "audio/music/A_New_Days_Hurry_cover.png"),
    MENU_1("Serene", "One Man Symphony",
        "audio/music/One Man Symphony - Serene.mp3",
        "audio/music/Reflective_District_cover.png"),
    ;

    public final String title;
    public final Music music;
    public final Texture cover;
    public final TextureRegionDrawable coverDrawable;
    public final String composer;

    GameMusic(String title, String composer, String audioFilePath, String coverFilePath) {
        this.title = title;
        this.music = Gdx.audio.newMusic(Gdx.files.internal(audioFilePath));
        this.cover = new Texture(coverFilePath);
        this.coverDrawable = new TextureRegionDrawable(cover);
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
        cover.dispose();
    }

}
