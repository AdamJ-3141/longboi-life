package com.spacecomplexity.longboilife.game.audio;

/**
 * A class to handle all the music.
 */
public class MusicHandler {

    public static MusicPlaylist currentPlaylist;

    public MusicHandler() { }

    /**
     * Set the current playlist and start it;
     * @param playlist the desired MusicPlaylist.
     */
    public void setCurrentPlaylist(MusicPlaylist playlist) {
        if (currentPlaylist != null) {
            stopPlaylist();
        }
        currentPlaylist = playlist;
        startPlaylist();
    }

    /**
     * Start the current playlist.
     */
    public void startPlaylist() {
        currentPlaylist.start();
    }

    /**
     * Stop the current playlist.
     */
    public void stopPlaylist() {
        currentPlaylist.stop();
    }

    public void dispose() {
        for (MusicPlaylist playlist : MusicPlaylist.values()) {
            playlist.dispose();
        }
    }
}
