package com.spacecomplexity.longboilife.game.audio;

public class AudioController {

    private static final AudioController instance = new AudioController();

    public static AudioController getInstance() { return instance; }

    private final MusicHandler musicHandler;
    public AudioController() {
        musicHandler = new MusicHandler();
    }

    public void playSound(SoundEffect sound) {
        sound.play();
    }

    public void startMusicPlaylist(MusicPlaylist musicPlaylist) {
        musicHandler.setCurrentPlaylist(musicPlaylist);
    }

    public MusicPlaylist getCurrentPlaylist() {
        return MusicHandler.currentPlaylist;
    }

    public void dispose() {
        musicHandler.dispose();
        for (SoundEffect sound : SoundEffect.values()) {
            sound.dispose();
        }
    }

}
