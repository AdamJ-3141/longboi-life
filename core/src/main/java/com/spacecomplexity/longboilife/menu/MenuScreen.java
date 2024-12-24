package com.spacecomplexity.longboilife.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Main;
import com.spacecomplexity.longboilife.MainInputManager;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.MusicPlaylist;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.ui.UIMusicInfo;
import com.spacecomplexity.longboilife.menu.menuUI.AchievementUI;
import com.spacecomplexity.longboilife.menu.menuUI.LeaderboardUI;
import com.spacecomplexity.longboilife.menu.menuUI.SettingsUI;

/**
 * Main class to control the menu screen.
 */
public class MenuScreen implements Screen {
    private final Main game;

    private final AudioController audio;

    private final Viewport viewport;

    private final Texture backgroundTexture;
    private final SpriteBatch batch;

    private final Stage stage;
    private final Skin skin;

    private UIElement leaderboardUI;
    private UIElement achievementsUI;
    private UIElement musicUI;
    private UIElement settingsUI;

    public MenuScreen(Main game) {
        this.game = game;
        this.audio = AudioController.getInstance();

        // Initialise viewport and drawing elements
        viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        // Load background texture
        backgroundTexture = new Texture(Gdx.files.internal("menu/background.png"));

        // Load UI skin for buttons
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));

        //sets the size of the window
        Gdx.graphics.setWindowedMode(740, 555);
    }

    @Override
    public void show() {

        MenuState.inMenu = true;

        audio.startMusicPlaylist(MusicPlaylist.MENU);

        // Table layout for menu alignment
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Initialise play button
        TextButton playButton = new TextButton("Play", skin, "round");
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audio.playSound(SoundEffect.BUTTON_CLICK);
                // Switch to game screen
                game.switchScreen(Main.ScreenType.GAME);
                audio.playSound(SoundEffect.GAME_BEGIN);
                MenuState.inMenu = false;
            }
        });

        // Initialise leaderboard button
        TextButton leaderboardButton = new TextButton("Leaderboard", skin, "round");
        leaderboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audio.playSound(SoundEffect.BUTTON_CLICK);
                // Open leaderboard pop-up
                MenuState.leaderboard = true;
            }
        });

        // Initialise leaderboard button
        TextButton achievementButton = new TextButton("Achievements", skin, "round");
        achievementButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audio.playSound(SoundEffect.BUTTON_CLICK);
                // Open achievements pop-up
                MenuState.achievement = true;
            }
        });

        // Initialise settings button
        TextButton settingsButton = new TextButton("Settings", skin, "round");
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audio.playSound(SoundEffect.BUTTON_CLICK);
                // Open settings UI
                SettingsUI.settingsVisible = true;
            }
        });

        // Initialise exit button
        TextButton exitButton = new TextButton("Exit", skin, "round");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                audio.playSound(SoundEffect.BUTTON_CLICK);
                // Exit the application
                Gdx.app.exit();
            }
        });

        // Add buttons to table
        table.pad(100).right().bottom();
        table.add(playButton);
        table.row();
        table.add(leaderboardButton).padTop(10);
        table.row();
        table.add(achievementButton).padTop(10);
        table.row();
        table.add(settingsButton).padTop(10);
        table.row();
        table.add(exitButton).padTop(10);

        // Allows UI to capture touch events
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager(), stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        leaderboardUI = new LeaderboardUI(viewport, table, skin);
        achievementsUI = new AchievementUI(viewport, table, skin);
        musicUI = new UIMusicInfo(viewport, table, skin);
        settingsUI = new SettingsUI(viewport, table, skin);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Draw background image
        batch.begin();
        batch.draw(backgroundTexture, (640 - 480) / 2f, 0, 480, 480);
        batch.end();

        // Draw and apply ui
        stage.act(delta);
        stage.draw();

        leaderboardUI.render();
        achievementsUI.render();
        musicUI.render();
        settingsUI.render();

        // Update the music volume to match the setting
        audio.updateMusicVolume();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        batch.dispose();
    }
}
