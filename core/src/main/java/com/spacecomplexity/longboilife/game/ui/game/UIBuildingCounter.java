package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;

import java.util.Arrays;

/**
 * Class to represent the UI building counter.
 */
public class UIBuildingCounter extends UIElement {
    private final Label[] counterLabels;

    /**
     * Initialise clock menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBuildingCounter(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        String[] buildingList = new String[BuildingCategory.values().length];
        Label[] buildingLabels = new Label[buildingList.length];
        counterLabels = new Label[buildingList.length];
        for (int i = 0; i < BuildingCategory.values().length; i++) {
            BuildingCategory category = BuildingCategory.values()[i];
            buildingList[i] = String.join(
                "\r\n",
                Arrays.stream(BuildingType.values())
                    .filter(type -> type.getCategory().equals(category))
                    .map(BuildingType::getDisplayName)
                    .toArray(String[]::new)
            );

            buildingLabels[i] = new Label(buildingList[i], skin);
            buildingLabels[i].setColor(Constants.categoryColours.get(category));
            buildingLabels[i].setFontScale(1f);

            counterLabels[i] = new Label(null, skin);
            counterLabels[i].setFontScale(1f);
            counterLabels[i].setColor(Color.WHITE);

            table.left().padLeft(15).add(buildingLabels[i]).fillX();
            table.add(counterLabels[i]).padLeft(5);
            table.row();
        }


        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(150, 310);
        placeTable();
    }

    public void render() {
        // Get the count of all buildings and display them
        for (int i = 0; i < BuildingCategory.values().length; i++) {
            int finalI = i;
            String buildingCount = String.join(
                "\r\n",
                Arrays.stream(BuildingType.values())
                    .filter(type -> type.getCategory() == BuildingCategory.values()[finalI])
                    .map((buildingType -> GameState.getState().getBuildingCount(buildingType).toString()))
                    .toArray(String[]::new)
            );
            counterLabels[i].setText(buildingCount);
        }
    }

    @Override
    protected void placeTable() {
        table.setPosition(0, uiViewport.getWorldHeight() - table.getHeight() - 45);
    }
}
