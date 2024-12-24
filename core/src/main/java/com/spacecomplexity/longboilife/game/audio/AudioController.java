package com.spacecomplexity.longboilife.game.audio;

/**
 * Static class that controls the game audio.
 */
public class AudioController {

    private static final AudioController instance = new AudioController();

    // Globals for audio volume
    public static float musicVolume = 0.5f;
    public static float soundVolume = 0.5f;

    public static AudioController getInstance() { return instance; }

    private final MusicHandler musicHandler = new MusicHandler();

    /**
     * Initialize the AudioController instance.
     */
    public AudioController() {}

    /**
     * Play a sound effect.
     * @param sound the sound to be played.
     */
    public void playSound(SoundEffect sound) {
        sound.play();
    }

    /**
     * End the current playlist if it exists and start a new playlist.
     * @param musicPlaylist the playlist to be played.
     */
    public void startMusicPlaylist(MusicPlaylist musicPlaylist) {
        musicHandler.setCurrentPlaylist(musicPlaylist);
    }

    public MusicPlaylist getCurrentPlaylist() {
        return MusicHandler.currentPlaylist;
    }

    /**
     * Sets the currently playing music to the global music volume.
     * <p>
     * Called every frame.
     */
    public void updateMusicVolume() {
        MusicHandler.currentPlaylist.getCurrentMusic().setVolume(musicVolume);
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
