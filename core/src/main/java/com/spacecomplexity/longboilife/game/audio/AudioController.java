package com.spacecomplexity.longboilife.game.audio;

/**
 * Static class that controls the game audio.
 */
public class AudioController {

    private static final AudioController instance = new AudioController();

    public static AudioController getInstance() { return instance; }

    private final MusicHandler musicHandler;

    /**
     * Initialize the AudioController instance.
     */
    public AudioController() {
        musicHandler = new MusicHandler();
    }

    /**
     * Play a sound effect.
     * @param sound the sound to be played.
     */
    public void playSound(SoundEffect sound) {
        sound.play();
    }

    /**
     * End the current playlist if it exists and start a new playlist.
     * @param musicPlaylist
     */
    public void startMusicPlaylist(MusicPlaylist musicPlaylist) {
        musicHandler.setCurrentPlaylist(musicPlaylist);
    }

    public MusicPlaylist getCurrentPlaylist() {
        return MusicHandler.currentPlaylist;
    }

    /**
     * Dispose of all audio objects.
     */
    public void dispose() {
        musicHandler.dispose();
        for (SoundEffect sound : SoundEffect.values()) {
            sound.dispose();
        }
    }

}
