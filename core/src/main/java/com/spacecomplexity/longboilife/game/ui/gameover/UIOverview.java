package com.spacecomplexity.longboilife.game.ui.gameover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.menu.leaderboard.LeaderboardElement;
import com.spacecomplexity.longboilife.menu.leaderboard.LeaderboardUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Class to represent the Overview UI after the game is completed.
 */
public class UIOverview extends UIElement {
    /**
     * Initialise overview elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIOverview(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        String overview = String.format("Game Over\r\nTotal Score: %,d", GameState.getState().totalScore);

        // Initialise label
        Label label = new Label(overview, skin);
        label.setAlignment(Align.center);
        label.setFontScale(1.3f);
        label.setColor(Color.WHITE);

        // Create a TextField
        TextField textField = new TextField("", skin);
        textField.setMessageText("Enter name to save");

        // Initialise button to save score
        TextButton saveButton = new TextButton("Save", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                // value is obtained from text box
                String name = textField.getText();
                if (Objects.equals(name, "")){
                    name = "Player";
                }

                // add score to the leaderboard
                List<LeaderboardElement> scores = Arrays.asList(LeaderboardUtils.loadScore());
                LeaderboardElement[] newScores = LeaderboardUtils.addScore(new LeaderboardElement(name, GameState.getState().totalScore), scores);
                LeaderboardUtils.saveScore(newScores);


                // Call the event to change screen to leaderboard
                EventHandler.getEventHandler().callEvent(EventHandler.Event.LEADERBOARD);
            }
        });

        // Adds label, buttons and text box onto table
        table.add(label).align(Align.center);
        table.row();
        table.add(textField).padTop(10).align(Align.center);
        table.row();
        table.add(saveButton).padTop(5).align(Align.center);
        table.row();

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(300, 130);
        placeTable();
    }

    public void render() {
    }

    @Override
    protected void placeTable() {
        table.setPosition((uiViewport.getWorldWidth() - table.getWidth()) / 2, uiViewport.getWorldHeight() - table.getHeight());
    }
}
