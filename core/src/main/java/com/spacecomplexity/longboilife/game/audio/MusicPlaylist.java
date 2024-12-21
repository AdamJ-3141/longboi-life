package com.spacecomplexity.longboilife.game.audio;

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

    public void start() {
        currentMusic = playlist[currentIndex];
        playMusic();
    }

    private void nextSong() {
        currentIndex = (currentIndex + 1) % playlistSize;
        currentMusic = playlist[currentIndex];
        playMusic();
    }

    private void playMusic() {
        currentMusic.play();
        currentMusic.music.setOnCompletionListener(music -> nextSong());
    }

    public void stop() {
        currentMusic.stop();
    }

    public void dispose() {
        for (GameMusic music : playlist) {
            music.dispose();
        }
    }
}
