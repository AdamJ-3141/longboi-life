package com.spacecomplexity.longboilife.game.audio;

/**
 * An enumeration of all the music playlists in the game.
 */
public enum MusicPlaylist {

    MENU(new GameMusic[]{GameMusic.MENU_1}),
    GAME(new GameMusic[]{GameMusic.GAME_1, GameMusic.GAME_2}),
    ;

    private final GameMusic[] playlist;
    private final int playlistSize;
    private int currentIndex;
    private GameMusic currentMusic;

    MusicPlaylist(GameMusic[] playlist) {
        this.playlist = playlist;
        playlistSize = playlist.length;
    }

    public GameMusic getCurrentMusic() {
        return currentMusic;
    }

    /**
     * Start playing the playlist.
     */
    public void start() {
        currentMusic = playlist[currentIndex];
        playMusic();
    }

    /**
     * Set the current music to the next one in the playlist,
     * looping back to the beginning if required.
     */
    private void nextSong() {
        currentIndex = (currentIndex + 1) % playlistSize;
        currentMusic = playlist[currentIndex];
        playMusic();
    }

    /**
     * Play the current music.
     */
    private void playMusic() {
        currentMusic.play();
        currentMusic.music.setOnCompletionListener(music -> nextSong());
    }

    /**
     * Stop the current music.
     */
    public void stop() {
        currentMusic.stop();
    }

    public void dispose() {
        for (GameMusic music : playlist) {
            music.dispose();
        }
    }
}
