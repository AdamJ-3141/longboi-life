package com.spacecomplexity.longboilife.leaderboards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

/**
 * Class that controls the leaderboard screen
 */
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

        // Load UI skin for buttons
        skin = new Skin(Gdx.files.internal("ui/skin/uiskin.json"));

        // Loads the leaderboard
        leaderboard = LeaderboardUtils.loadScore();
    }

    @Override
    public void show() {
        // Table layout
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // puts names from leaderboard into a column
        String leaderboardName = "Name:\r\n\n";
        for (LeaderboardElement element : leaderboard) {
            leaderboardName += element.getName() + "\r\n\n";
        }

        // Initialise names label
        Label labelName = new Label(leaderboardName, skin);
        labelName.setAlignment(Align.center);
        labelName.setFontScale(1.4f);
        labelName.setColor(Color.WHITE);

        // Puts scores from leaderboard into a column
        String leaderboardScore = "Score:\r\n\n";
        for (LeaderboardElement leaderboardElement : leaderboard) {
            leaderboardScore += leaderboardElement.getScore() + "\r\n\n";
        }

        // Initialises scores label
        Label labelScore = new Label(leaderboardScore, skin);
        labelScore.setAlignment(Align.center);
        labelScore.setFontScale(1.4f);
        labelScore.setColor(Color.WHITE);

        // Initialise exit button
        TextButton backButton = new TextButton("Menu", skin, "round");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch to Menu screen
                game.switchScreen(Main.ScreenType.MENU);
            }
        });

        // Place labels and button onto table
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
