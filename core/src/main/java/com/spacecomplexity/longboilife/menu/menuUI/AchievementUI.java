package com.spacecomplexity.longboilife.menu.menuUI;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.menu.MenuState;

/**
 * Class that uses UIElement abstract class to create a display for the achievements
 */
public class AchievementUI extends UIElement {
    /**
     * Initialise achievement pop up window with the achievements being rendered
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public AchievementUI(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // creates achievement title label
        Label achievementLabel = new Label("Achievements:", skin);
        achievementLabel.setFontScale(1.2f);
        table.add(achievementLabel).colspan(2).align(Align.center).padBottom(15);
        table.row();

        for (AchievementType type : AchievementType.values()) {
            // Construct a new table for each achievement type.
            Table achievementTable = constructTable(type, skin);
            // Add the new table to the main table.
            table.add(achievementTable);
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
                AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                // Close the achievement pop-up
                MenuState.achievement = false;
            }
        });

        //places the exit button
        table.row();
        table.add(exitButton).colspan(2).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(400, 380);
        placeTable();
    }

    /**
     * creates a formatted table for the achievement type passed too it
     * @param type The achievement type
     * @param skin The current skin
     * @return Returns a Table containing the information about the achievement
     */
    private Table constructTable(AchievementType type, Skin skin) {
        Table currentTable = new Table();
        Label tempTitleLabel = new Label(type.title, skin);
        Label tempDescriptionLabel = new Label(type.description, skin);
        Label tempScoreChangeLabel = new Label((type.scoreChange >= 0 ? "+" : "-") + type.scoreChange, skin);
        currentTable.add(tempTitleLabel);
        currentTable.row();
        currentTable.add(tempDescriptionLabel);
        currentTable.row();
        currentTable.add(tempScoreChangeLabel);
        currentTable.padLeft(15);
        currentTable.padRight(15);
        currentTable.padBottom(8);
        return currentTable;
    }

    /**
     * renders the achievement window
     */
    public void render() {
        placeTable();
    }

    /**
     * places table on screen if the achievement is currently being displayed
     */
    @Override
    protected void placeTable() {
        if (MenuState.achievement) {
            table.setPosition(120, 50);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
