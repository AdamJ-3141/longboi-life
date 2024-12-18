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

        String leftAchievementString = "";
        String rightAchievementString = "";
        int value = 0;
        for (AchievementType type : AchievementType.values()) {
            String achievementString = "";
            achievementString += type.title + "\n\r" + type.description + "\n\r";
            if (type.scoreChange >= 0) {
                achievementString += "+";
            } else {
                achievementString += "-";
            }
            achievementString += type.scoreChange + " score \n\r";
            achievementString += "\n";
            if (value%2 == 0) {
                leftAchievementString += achievementString;
                System.out.println("left");
            } else {
                rightAchievementString += achievementString;
                System.out.println("right");
            }
            value++;
        }

        // Initialise names label
        Label leftAchievementLabel = new Label(leftAchievementString, skin);
        leftAchievementLabel.setAlignment(Align.center);
        leftAchievementLabel.setFontScale(1f);
        leftAchievementLabel.setColor(Color.WHITE);

        Label rightAchievementLabel = new Label(rightAchievementString, skin);
        rightAchievementLabel.setAlignment(Align.center);
        rightAchievementLabel.setFontScale(1f);
        rightAchievementLabel.setColor(Color.WHITE);

        // Initialise exit button
        TextButton exitButton = new TextButton("Back", skin, "round");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Close the achievement pop-up
                MenuState.achievement = false;
            }
        });

        // Place elements onto table
        Table labelTable = new Table(skin);
        labelTable.add(leftAchievementString).align(Align.center).size(50);
        labelTable.add().pad(100).padTop(150).padBottom(150);
        labelTable.add(rightAchievementLabel).align(Align.center).size(50);
        labelTable.row();
        labelTable.add();
        labelTable.add(exitButton).align(Align.center);
        table.add(labelTable);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(420, 380);
        placeTable();
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
