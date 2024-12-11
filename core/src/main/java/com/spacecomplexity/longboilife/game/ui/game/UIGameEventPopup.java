package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

/**
 * Class to represent an event occuring in game.
 */
public class UIGameEventPopup extends UIElement {
    private final Skin skin;

    /**
     * Initialise event popup elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIGameEventPopup(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        this.skin = skin;

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();

        EventHandler.getEventHandler().createEvent(EventHandler.Event.CLOSE_GAMEEVENT_POPUP, (params) -> {
            closePopup();
            return null;
        });
    }

    public void render() {
    }

    /**
     * Close the popup.
     */
    public void closePopup() {
        // Hide the build menu
        table.setVisible(false);
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 150);
        table.setPosition(0, 55);
    }
}
