package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.gameevent.GameEventType;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

/**
 * Class to represent an event occuring in game.
 */
public class UIGameEventPopup extends UIElement {
    private final Skin skin;
    private Label title;
    private Label description;

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

        // Initialise title label
        title = new Label(null, skin);
        title.setFontScale(1.5f);
        title.setColor(Color.WHITE);

        description = new Label(null, skin);
        description.setFontScale(1f);
        description.setColor(Color.WHITE);

        // Place label onto table
        table.add(title).align(Align.center);
        table.row();
        table.add(description).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();
        closePopup();

        EventHandler.getEventHandler().createEvent(EventHandler.Event.OPEN_GAMEEVENT_POPUP, (params) -> {
            if (!(params[0] instanceof GameEventType)) {
                return null;
            }
            showPopup((GameEventType) params[0]);
            return null;
        });

        EventHandler.getEventHandler().createEvent(EventHandler.Event.CLOSE_GAMEEVENT_POPUP, (params) -> {
            closePopup();
            return null;
        });
    }

    public void render() {
    }

    public void showPopup(GameEventType gameEvent) {
        title.setText(gameEvent.getDisplayName());
        title.setColor(gameEvent.getColor());
        description.setText(gameEvent.getEventMessage());
        table.setVisible(true);
    }

    /**
     * Close the popup.
     */
    private void closePopup() {
        // Hide the popup
        table.setVisible(false);
    }

    @Override
    protected void placeTable() {
        table.setSize(300, 60);
        table.setPosition(uiViewport.getWorldWidth() - table.getWidth() - 180,
                uiViewport.getWorldHeight() - table.getHeight());
    }
}
