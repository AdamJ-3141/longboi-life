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
import java.util.HashMap;

/**
 * Class to represent the UI building counter.
 */
public class UIBuildingCounter extends UIElement {
    private final Label[] counterLabels;
    private final HashMap<BuildingCategory, Label> buildingLabels;

    /**
     * Initialise building counter menu elements.
     * <p>
     * Creates two labels per {@link BuildingCategory}, the list of building types,
     * and the list of building counters. The list of building types will be a
     * different colour based on the category.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBuildingCounter(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise the lists of building category labels.
        String[] buildingList = new String[BuildingCategory.values().length];
        buildingLabels = new HashMap<>();
        // buildingLabels = new Label[buildingList.length];
        counterLabels = new Label[buildingList.length];

        // For each category, create one of each kind of label.
        for (int i = 0; i < BuildingCategory.values().length; i++) {

            // Get a list of all the BuildingType display names of the category.
            BuildingCategory category = BuildingCategory.values()[i];
            buildingList[i] = String.join(
                "\r\n",
                Arrays.stream(BuildingType.values())
                    .filter(type -> type.getCategory().equals(category))
                    .map(BuildingType::getDisplayName)
                    .toArray(String[]::new)
            );

            // Add the new building type and counter labels to the table.
            buildingLabels.put(category, new Label(buildingList[i], skin));
            buildingLabels.get(category).setColor(Constants.categoryColours.get(category));
            buildingLabels.get(category).setFontScale(1f);

            counterLabels[i] = new Label(null, skin);
            counterLabels[i].setFontScale(1f);
            counterLabels[i].setColor(Color.WHITE);

            table.left().padLeft(15).add(buildingLabels.get(category)).fillX();
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
            // Update each category's building count label.
            counterLabels[i].setText(buildingCount);

            if (!GameState.getState().paused) {
                for (BuildingCategory category : buildingLabels.keySet()) {
                    buildingLabels.get(category).setColor(Constants.categoryColours.get(category));
                }
            }
        }
    }

    @Override
    protected void placeTable() {
        table.setPosition(0, uiViewport.getWorldHeight() - table.getHeight() - 45);
    }
}
