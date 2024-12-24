package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.GameUtils;
import com.spacecomplexity.longboilife.game.utils.InterfaceUtils;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Class to represent the Build Menu UI.
 */
public class UIBuildMenu extends UIElement {
    private final Skin skin;
    private final MoneyGenTooltip tooltip;

    /**
     * Initialise build menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBuildMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        this.skin = skin;

        this.tooltip = new MoneyGenTooltip(uiViewport, parentTable, skin);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();

        // Initially close the menu
        closeMenu();

        // Close menu when receiving an event to do so
        EventHandler.getEventHandler().createEvent(EventHandler.Event.CLOSE_BUILD_MENU, (params) -> {
            closeMenu();
            return null;
        });
    }

    /**
     * Private class for the tooltip
     */
    private static class MoneyGenTooltip extends UIElement {
        Label moneyLabel;
        public BuildingType buildingType;
        /**
         * Initialise base UI elements
         *
         * @param uiViewport  the viewport used to render UI.
         * @param parentTable the table to render this element onto.
         * @param skin        the provided skin.
         */
        public MoneyGenTooltip(Viewport uiViewport, Table parentTable, Skin skin) {
            super(uiViewport, parentTable, skin);

            moneyLabel = new Label("", skin);
            moneyLabel.setColor(Color.GREEN);

            table.add(moneyLabel);
            table.setSize(120, 30);
            table.setBackground(skin.getDrawable("panel1"));
        }

        /**
         * Render this UI element.
         */
        @Override
        public void render() {
            if (buildingType != null) {
                moneyLabel.setText("+" +
                    NumberFormat.getCurrencyInstance(Locale.UK)
                    .format(GameUtils.getMoneyGenerated(buildingType)) + " / 5s");
            }
            placeTable();
        }

        /**
         * Set the table position relative to the viewport.
         */
        @Override
        protected void placeTable() {
            if (buildingType != null) {
                // Sets the position of the table to the current mouse position.
                Vector2 mouse = InterfaceUtils.getMousePositionInViewport(uiViewport);
                table.setPosition(mouse.x + 4, mouse.y + 4);
            }
            else {
                table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
            }
        }
    }

    public void render() {
        tooltip.render();
    }


    /**
     * Open the build menu ready for the user to place buildings from a specific {@link BuildingCategory}.
     *
     * @param category specific category of buildings to show.
     */
    public void openMenu(BuildingCategory category) {
        // CLear previous buildings from the table
        table.clear();

        // Get list of all buildings to display on this menu
        BuildingType[] buildings = BuildingType.getBuildingsOfType(category);

        // Place building buttons on separate table for condensed styling
        Table buildingButtonsTable = new Table();
        for (BuildingType building : buildings) {
            // Get building texture
            TextureRegionDrawable texture = new TextureRegionDrawable(building.getTexture());
            // Set the size of the texture keeping its aspect ratio
            float textureSize = table.getHeight() - 75;
            Vector2Int buildingSize = building.getSize();
            float maxDimension = Math.max(buildingSize.x, buildingSize.y);
            texture.setMinSize(textureSize * (buildingSize.x / maxDimension), textureSize * (buildingSize.y / maxDimension));

            // Initialise building button
            ImageButton button = new ImageButton(texture);
            // Initialise place building sequence when clicked
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                    EventHandler.getEventHandler().callEvent(EventHandler.Event.CANCEL_OPERATIONS);

                    GameState.getState().placingBuilding = building;
                }
            });

            // Initialise building labels
            Label titleLabel = new Label(building.getDisplayName(), skin);
            Label costLabel = new Label(NumberFormat.getCurrencyInstance(Locale.UK).format(building.getCost()), skin);
            Label sizeLabel = new Label(String.format("%dx%d", building.getSize().x, building.getSize().y), skin);

            // create container for UI elements relating to this building
            Table buildingTable = new Table();
            // Add building UI elements into there container
            buildingTable.add(button);
            buildingTable.row();
            buildingTable.add(titleLabel).padTop(2);
            buildingTable.row();
            buildingTable.add(costLabel).padTop(2);
            buildingTable.row();
            buildingTable.add(sizeLabel).padTop(2);

            // Add the container to the building table
            buildingButtonsTable.add(buildingTable).expandX().expandY().fillY().padLeft(2);
            // Add event listeners for mouse moving into the button for tooltip
            if (category == BuildingCategory.ACCOMMODATION) {
                buildingTable.addListener(new ClickListener() {
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.buildingType = building;
                    }
                });
                buildingTable.addListener(new ClickListener() {
                    public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        tooltip.buildingType = null;
                    }
                });
            }
        }
        // Add the buildings table onto the main table
        table.add(buildingButtonsTable).expandX().left();

        // Show the build menu
        table.setVisible(true);
    }

    /**
     * Close the build menu.
     */
    public void closeMenu() {
        // Hide the build menu
        table.setVisible(false);
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 150);
        table.setPosition(0, 55);
    }
}
