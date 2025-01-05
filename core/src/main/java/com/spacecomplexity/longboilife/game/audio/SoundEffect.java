package com.spacecomplexity.longboilife.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

/**
 * An enumeration of all sound effects in the game.
 */
public enum SoundEffect {

    BUILD_BUILDING(Gdx.files.internal("audio/sound_effects/build_building.wav")),
    BUILD_PATHWAY(Gdx.files.internal("audio/sound_effects/build_pathway.wav")),
    DESTROY(Gdx.files.internal("audio/sound_effects/destroyed.wav")),
    BUTTON_CLICK(Gdx.files.internal("audio/sound_effects/button_click.wav")),
    MONEY_UP(Gdx.files.internal("audio/sound_effects/money_up.wav")),
    SATISFACTION_UP(Gdx.files.internal("audio/sound_effects/satisfaction_up.wav")),
    SATISFACTION_DOWN(Gdx.files.internal("audio/sound_effects/satisfaction_down.wav")),
    GAME_BEGIN(Gdx.files.internal("audio/sound_effects/game_begin.wav")),
    GAME_OVER(Gdx.files.internal("audio/sound_effects/game_end.wav")),
    ;

    public final FileHandle file;
    public final Sound sound;

    /**
     * Play the sound effect.
     */
    public void play() {
        sound.play(AudioController.soundVolume);
    }

    public void dispose() { sound.dispose(); }

    /**
     * Initialises the sound effect with an audio file.
     * @param file must be wav-16, ogg, or mp3.
     */
    SoundEffect(FileHandle file) {
        this.file = file;
        this.sound = Gdx.audio.newSound(file);
    }
}
