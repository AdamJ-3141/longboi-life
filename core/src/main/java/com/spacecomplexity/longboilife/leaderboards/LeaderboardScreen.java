package com.spacecomplexity.longboilife.leaderboards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Main;
import com.spacecomplexity.longboilife.MainInputManager;

public class LeaderboardScreen implements Screen {
    private final Main game;

    private Viewport viewport;

    private Stage stage;
    private Skin skin;

    private LeaderboardElement[] leaderboard;

    public LeaderboardScreen(Main game) {
        this.game = game;

        // Initialise viewport and drawing elements
        viewport = new FitViewport(640, 480);
        stage = new Stage(viewport);

        // Load background texture
//        backgroundTexture = new Texture(Gdx.files.internal("menu/background.png"));

        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));

        leaderboard = LeaderboardUtils.loadScore();
    }

    @Override
    public void show() {
        // Table layout for menu alignment
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        String leaderboardName = "Name:\r\n\n";
        for (LeaderboardElement element : leaderboard) {
            leaderboardName += element.getName() + "\r\n\n";
        }

        // Initialise label
        Label labelName = new Label(leaderboardName, skin);
        labelName.setAlignment(Align.center);
        labelName.setFontScale(1.4f);
        labelName.setColor(Color.WHITE);

        String leaderboardScore = "Score:\r\n\n";
        for (LeaderboardElement leaderboardElement : leaderboard) {
            leaderboardScore += leaderboardElement.getScore() + "\r\n\n";
        }

        Label labelScore = new Label(leaderboardScore, skin);
        labelScore.setAlignment(Align.center);
        labelScore.setFontScale(1.4f);
        labelScore.setColor(Color.WHITE);

        // Initialise exit button
        TextButton backButton = new TextButton("Menu", skin, "round");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Exit the application
                game.switchScreen(Main.ScreenType.MENU);
            }
        });

        // Place label onto table
        table.add(labelName).align(Align.center).size(50);
        table.add().pad(50);
        table.add(labelScore).align(Align.center).size(50);
        table.row();
        table.add();
        table.add(backButton).padTop(20).align(Align.center).padLeft(10);

        // Allows UI to capture touch events
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager(), stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Draw and apply ui
        stage.act(delta);
        stage.draw();
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
    }
}
