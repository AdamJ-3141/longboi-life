package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Class to represent the Money UI.
 */
public class UIScoreMenu extends UIElement {
    private Label label;

    /**
     * Initialise score menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIScoreMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise score label
        label = new Label(null, skin);
        label.setFontScale(1.2f);
        label.setColor(Color.WHITE);

        // Place label onto table
        table.add(label).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(120, 40);
        placeTable();
    }

    public void render() {
        label.setText(GameState.getState().totalScore);
    }

    @Override
    protected void placeTable() {
        table.setPosition(uiViewport.getWorldWidth() - table.getWidth(), uiViewport.getWorldHeight() - table.getHeight() - 85);
    }
}
