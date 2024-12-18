package com.spacecomplexity.longboilife.leaderboards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.menu.MenuState;

/**
 * Class that uses UIElement abstract class to create a display for the leaderboard
 */
public class LeaderboardUI extends UIElement {
    private Label nameLabel;
    private Label scoreLabel;
    private LeaderboardElement[] leaderboard;

    /**
     * Initialise Leaderboard window.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public LeaderboardUI(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise names label
        nameLabel = new Label(null, skin);
        nameLabel.setAlignment(Align.center);
        nameLabel.setFontScale(1.4f);
        nameLabel.setColor(Color.WHITE);

        // Initialises scores label
        scoreLabel = new Label(null, skin);
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setFontScale(1.4f);
        scoreLabel.setColor(Color.WHITE);

        // Initialise exit button
        TextButton exitButton = new TextButton("Back", skin, "round");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Close the leaderboard
                MenuState.leaderboard = false;
            }
        });

        // Place elements onto table
        Table labelTable = new Table(skin);
        labelTable.add(nameLabel).align(Align.center).size(50);
        labelTable.add().pad(50).padTop(100).padBottom(100);
        labelTable.add(scoreLabel).align(Align.center).size(50);
        labelTable.row();
        labelTable.add();
        labelTable.add(exitButton).align(Align.center);
        table.add(labelTable);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(350, 320);
        placeTable();
    }

    /**
     * renders the leaderboard window, updates the text if its currently being displayed
     */
    public void render() {
        if (MenuState.leaderboard) {
            // Loads the leaderboard
            leaderboard = LeaderboardUtils.loadScore();

            // puts the names in a column
            String leaderboardName = "Name:\r\n\n";
            for (LeaderboardElement element : leaderboard) {
                leaderboardName += element.getName() + "\r\n\n";
            }
            nameLabel.setText(leaderboardName);

            // puts the scores in a column
            String leaderboardScore = "Score:\r\n\n";
            for (LeaderboardElement leaderboardElement : leaderboard) {
                leaderboardScore += leaderboardElement.getScore() + "\r\n\n";
            }
            scoreLabel.setText(leaderboardScore);
        }
        placeTable();
    }

    /**
     * places table on screen if the leaderboard is currently being displayed
     */
    @Override
    protected void placeTable() {
        if (MenuState.leaderboard) {
            table.setPosition(170, 90);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
