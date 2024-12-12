package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;

/**
 * Class that renders the achievement popup
 */
public class UIAchievementPopup extends UIElement {
    private Label achievementLabel;
    private Label scoreLabel;

    /**
     * Initialise Achievement popup elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIAchievementPopup(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise label
        Label label = new Label("Achievement unlocked!!\r\n", skin);
        label.setFontScale(1f);
        label.setColor(Color.WHITE);

        // Initialise achievement label
        achievementLabel = new Label(null, skin);
        achievementLabel.setFontScale(1f);
        achievementLabel.setColor(Color.WHITE);

        // Initialise score label
        scoreLabel = new Label(null, skin);
        scoreLabel.setFontScale(1f);
        scoreLabel.setColor(Color.WHITE);

        // Place elements onto table
        Table labelTable = new Table(skin);
        labelTable.add(label);
        labelTable.row();
        labelTable.add(achievementLabel);
        labelTable.row();
        labelTable.add(scoreLabel).padTop(10);
        table.add(labelTable);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(185, 100);
        placeTable();
    }

    /**
     * renders the Achievement window, updates the text if an achievement has been completed
     */
    public void render() {
        if (GameState.getState().currentAchievement != null) {
            AchievementType currentAchievement = GameState.getState().currentAchievement;
            achievementLabel.setText(currentAchievement.displayName);
            String scoreChange = "";
            if (currentAchievement.scoreChange>0) {
                scoreChange += "+";
            }
            scoreChange += Integer.toString(currentAchievement.scoreChange);
            scoreLabel.setText(scoreChange);
        }
        placeTable();
    }

    /**
     * places table of screen if there is no current achievement
     */
    @Override
    protected void placeTable() {
        if (GameState.getState().currentAchievement != null) {
            table.setPosition(uiViewport.getWorldWidth()-table.getWidth(), 50);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
