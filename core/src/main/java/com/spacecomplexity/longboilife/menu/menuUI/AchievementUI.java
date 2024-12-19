package com.spacecomplexity.longboilife.menu.menuUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.menu.MenuState;

/**
 * Class that uses UIElement abstract class to create a display for the leaderboard
 */
public class AchievementUI extends UIElement {
    /**
     * Initialise Leaderboard window.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public AchievementUI(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);
        table.debug();
        Table[] achievementTables = new Table[AchievementType.values().length];
        for (AchievementType type : AchievementType.values()) {
            // Construct a new table for each achievement type.
            achievementTables[type.ordinal()] = constructTable(type, skin);
            // Add the new table to the main table.
            table.add(achievementTables[type.ordinal()]);
            // New row after every second table.
            if (type.ordinal() % 2 == 1) {
                table.row();
            }
        }

        // Initialise exit button
        TextButton exitButton = new TextButton("Back", skin, "round");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Close the achievement pop-up
                MenuState.achievement = false;
            }
        });

        table.row();
        table.add(exitButton).colspan(2).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(420, 380);
        placeTable();
    }

    private static Table constructTable(AchievementType type, Skin skin) {
        Table currentTable = new Table();
        currentTable.debug();
        Label tempTitleLabel = new Label(type.title, skin);
        Label tempDescriptionLabel = new Label(type.description, skin);
        Label tempScoreChangeLabel = new Label((type.scoreChange >= 0 ? "+" : "-") + type.scoreChange, skin);
        currentTable.add(tempTitleLabel);
        currentTable.row();
        currentTable.add(tempDescriptionLabel);
        currentTable.row();
        currentTable.add(tempScoreChangeLabel);
        return currentTable;
    }

    /**
     * renders the leaderboard window, updates the text if its currently being displayed
     */
    public void render() {
        placeTable();
    }

    /**
     * places table on screen if the leaderboard is currently being displayed
     */
    @Override
    protected void placeTable() {
        if (MenuState.achievement) {
            table.setPosition(110, 50);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
