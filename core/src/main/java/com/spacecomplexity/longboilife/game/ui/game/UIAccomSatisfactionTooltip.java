package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.Keybindings;
import com.spacecomplexity.longboilife.game.ui.UIElement;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.AccomSatisfactionDetail;
import com.spacecomplexity.longboilife.game.utils.InterfaceUtils;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexity.longboilife.game.world.World;
import com.spacecomplexity.longboilife.game.utils.GameUtils;

import java.util.HashMap;

/**
 * Class that renders the accommodation satisfaction tooltips.
 */
public class UIAccomSatisfactionTooltip extends UIElement {

    private Boolean hovering = false;
    private final Label satisfactionLabel;
    private final Label hintLabel;
    private final Label multLabel;
    private final Label multTitle;
    private final Label modTitle;
    private final Label modRelValue;
    private final Label modAbsValue;
    HashMap<BuildingCategory, Label> categorySatisfaction = new HashMap<>();
    HashMap<BuildingCategory, Label> categoryTitles = new HashMap<>();

    /**
     * Initialise the tooltip elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIAccomSatisfactionTooltip(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Low Detail
        satisfactionLabel = new Label(null, skin);
        Label titleLabel = new Label("Satisfaction: ", skin);
        String keyName = Input.Keys.toString(Keybindings.SHOW_DETAIL.getKey());
        hintLabel = new Label(String.format("Hold %s for more.", keyName), skin);
        hintLabel.setFontScale(0.8f);
        hintLabel.setColor(Color.GRAY);
        titleLabel.setFontScale(1f);
        satisfactionLabel.setFontScale(1f);
        table.add(titleLabel).right().padTop(5);
        table.add(satisfactionLabel).left().padTop(5);
        table.row();
        table.add(hintLabel).colspan(2).padBottom(5);

        // High Detail

        for (BuildingCategory category : Constants.satisfactoryDistance.keySet()) {
            categorySatisfaction.put(category, new Label(null, skin));
            categoryTitles.put(category, new Label(category.getDisplayName() + ": ", skin));
        }

        multTitle = new Label("Quality: ", skin);
        multLabel = new Label(null, skin);

        modTitle = new Label("Events: ", skin);
        modRelValue = new Label(null, skin);
        modAbsValue = new Label(null, skin);

        // setup
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(160, 50);
    }

    /**
     * Called each frame to render the UI.
     */
    @Override
    public void render() {
        // Removes high detail labels.
        for (BuildingCategory category : Constants.satisfactoryDistance.keySet()) {
            table.removeActor(categoryTitles.get(category));
            table.removeActor(categorySatisfaction.get(category));
        }
        table.removeActor(multTitle);
        table.removeActor(multLabel);
        table.removeActor(modTitle);
        table.removeActor(modRelValue);
        table.removeActor(modAbsValue);
        table.setSize(160, 50);

        // Fetches the current game state.
        GameState gameState = GameState.getState();
        World world = GameState.getState().gameWorld;

        // Gets the Tile that the mouse is currently over.
        Vector2Int mouse = GameUtils.getMouseOnGrid(world);
        Building building = world.getTile(mouse).getBuildingRef();
        if (building != null && building.getType().getCategory() == BuildingCategory.ACCOMMODATION) {
            hovering = true;
            float satisfaction = gameState.accomSatisfaction.get(building).totalSatisfaction;

            // Sets the satisfaction label's text and colour based on the building's satisfaction.
            satisfactionLabel.setText(String.format("  %6.2f%%", satisfaction * 100));
            colourLabel(satisfactionLabel, satisfaction);

            // Checks whether the show detail key is pressed.
            if (Gdx.input.isKeyPressed(Keybindings.SHOW_DETAIL.getKey())) {
                table.removeActor(hintLabel);
                table.setSize(160, 150);

                AccomSatisfactionDetail detail = gameState.accomSatisfaction.get(building);

                // Adds all the category satisfactions to the table, coloured appropriately.
                for (BuildingCategory cat : categorySatisfaction.keySet()) {
                    table.row();
                    Label catSat = categorySatisfaction.get(cat);
                    Label catTitle = categoryTitles.get(cat);
                    catSat.setText(String.format("  %6.2f%%", detail.categoryScores.get(cat) * 100));
                    colourLabel(catSat, detail.categoryScores.get(cat));
                    table.add(catTitle).right();
                    table.add(catSat).left();
                }
                table.row();

                // Adds accommodation quality multiplier.
                table.add(multTitle).right();
                multLabel.setText(String.format("x%6.2f",
                    detail.qualityMultiplier));

                multLabel.setColor(
                    detail.qualityMultiplier >= 1 ? Color.GREEN :
                        detail.qualityMultiplier >= 0.7 ? Color.YELLOW :
                            Color.RED);

                table.add(multLabel).left();
                table.row();

                float relmod = detail.satisfactionModifier.relative * gameState.globalSatisfactionModifier.relative;
                float absmod = detail.satisfactionModifier.absolute + gameState.globalSatisfactionModifier.absolute;

                modRelValue.setText(String.format("x%6.2f", relmod));
                modRelValue.setColor(
                    relmod == 1 ? Color.WHITE :
                        relmod > 1 ? Color.GREEN :
                            Color.RED);

                modAbsValue.setText(String.format((absmod >= 0 ? "+%6.2f%%" : "-%6.2f%%"), 100 * absmod));
                modAbsValue.setColor(
                    absmod == 0 ? Color.WHITE :
                        absmod > 0 ? Color.GREEN :
                            Color.RED);

                table.add(modTitle).right();
                table.add(modRelValue).left();
                table.row();
                table.add();
                table.add(modAbsValue).left();
            }
        } else {
            // If the mouse is not currently over an accommodation building.
            hovering = false;
        }

        placeTable();
    }

    /**
     * Set the colour of a label based on the value given.
     * @param label the Label to colour.
     * @param value the value to colour based on.
     */
    private static void colourLabel(Label label, float value) {
        label.setColor(value >= 1 ? Color.GREEN :
            value >= 0.7 ? Color.YELLOW :
                value >= 0.4 ? Color.ORANGE :
                    Color.RED);
    }

    /**
     * Places table off-screen if not hovering over accommodation, otherwise place it at the
     * mouse pointer.
     */
    @Override
    protected void placeTable() {
        if (hovering) {
            // Sets the position of the table to the current mouse position.
            Vector2 mouse = InterfaceUtils.getMousePositionInViewport(uiViewport);
            table.setPosition(mouse.x, mouse.y);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
