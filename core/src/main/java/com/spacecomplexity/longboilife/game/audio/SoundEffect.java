package com.spacecomplexity.longboilife.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public enum SoundEffect {

//    BUILD_BUILDING(Gdx.files.internal("")),
    BUILD_PATHWAY(Gdx.files.internal("audio/sound_effects/build_pathway.wav")),
    DESTROY(Gdx.files.internal("audio/sound_effects/destroyed.wav")),
    BUTTON_CLICK(Gdx.files.internal("audio/sound_effects/button_click.wav")),
//    MONEY_UP(Gdx.files.internal("")),
//    SATISFACTION_UP(Gdx.files.internal("")),
//    SATISFACTION_DOWN(Gdx.files.internal("")),
    GAME_BEGIN(Gdx.files.internal("audio/sound_effects/game_begin.wav")),
    GAME_OVER(Gdx.files.internal("audio/sound_effects/game_end.wav")),
    ;

    public final FileHandle file;
    public final Sound sound;

    public void play() {
        sound.play();
    }

    public void dispose() { sound.dispose(); }

    SoundEffect(FileHandle file) {
        this.file = file;
        this.sound = Gdx.audio.newSound(file);
    }
}
