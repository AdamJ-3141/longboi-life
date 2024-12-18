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
    private Label titleLabel;
    private Label descriptionLabel;

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

        // Initialise achievement title label
        titleLabel = new Label(null, skin);
        titleLabel.setFontScale(1f);
        titleLabel.setColor(Color.WHITE);

        // Initialise description label
        descriptionLabel = new Label(null, skin);
        descriptionLabel.setFontScale(0.9f);
        descriptionLabel.setColor(Color.WHITE);

        // Place elements onto table
        Table labelTable = new Table(skin);
        labelTable.add(label);
        labelTable.row();
        labelTable.add(titleLabel);
        labelTable.row();
        labelTable.add(descriptionLabel).padTop(5);
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
            titleLabel.setText(currentAchievement.title);
            descriptionLabel.setText(currentAchievement.description);
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
