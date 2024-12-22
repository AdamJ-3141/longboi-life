package com.spacecomplexity.longboilife.game.audio;


public class MusicHandler {

    public static MusicPlaylist currentPlaylist;

    public MusicHandler() { }

    public void setCurrentPlaylist(MusicPlaylist playlist) {
        if (currentPlaylist != null) {
            stopPlaylist();
        }
        currentPlaylist = playlist;
        startPlaylist();
    }

    public void startPlaylist() {
        currentPlaylist.start();
    }

    public void stopPlaylist() {
        currentPlaylist.stop();
    }

    public void dispose() {
        for (MusicPlaylist playlist : MusicPlaylist.values()) {
            playlist.dispose();
        }
    }
}
