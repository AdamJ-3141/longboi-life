package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.InterfaceUtils;
import com.spacecomplexity.longboilife.menu.menuUI.SettingsUI;

/**
 * Class to represent the Bottom Menu UI.
 */
public class UIBottomMenu extends UIElement {
    private final Texture pauseTexture;
    private final TextureRegionDrawable pauseDrawable;
    private final Texture playTexture;
    private final TextureRegionDrawable playDrawable;

    private final UIBuildMenu buildMenu;
    private final UIPauseScreen pauseScreen;

    /**
     * Initialise bottom menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBottomMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        EventHandler eventHandler = EventHandler.getEventHandler();

        buildMenu = new UIBuildMenu(uiViewport, parentTable, skin);
        pauseScreen = new UIPauseScreen(uiViewport, parentTable, skin);

        // Place building buttons on separate table for condensed styling
        Table buildingButtonsTable = new Table(skin);
        // Initialise building buttons
        for (BuildingCategory category : BuildingCategory.values()) {
            TextButton button = new TextButton(category.getDisplayName(), skin);

            // On click execute function to open the buildMenuTable on the specific category
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                    buildMenu.openMenu(category);
                }
            });

            buildingButtonsTable.add(button).expandX().padLeft(2);
        }
        // Add the buttons onto the main table
        table.add(buildingButtonsTable).expandX().left();

        // Load play/pause/settings textures as drawables
        float textureSize = 25;
        pauseTexture = new Texture(Gdx.files.internal("ui/buttons/pause.png"));
        pauseDrawable = new TextureRegionDrawable(pauseTexture);
        pauseDrawable.setMinSize(textureSize, textureSize);

        playTexture = new Texture(Gdx.files.internal("ui/buttons/play.png"));
        playDrawable = new TextureRegionDrawable(playTexture);
        playDrawable.setMinSize(textureSize, textureSize);

        Texture settingsTexture = new Texture(Gdx.files.internal("ui/buttons/settings.png"));
        TextureRegionDrawable settingsDrawable = new TextureRegionDrawable(settingsTexture);
        settingsDrawable.setMinSize(textureSize, textureSize);

        // Initialise pause button
        ImageButton pauseButton = new ImageButton(skin);
        pauseButton.setSize(textureSize, textureSize);
        pauseButton.getStyle().up = pauseDrawable;
        pauseButton.getStyle().down = pauseDrawable;

        // Initialise settings button
        ImageButton settingsButton = new ImageButton(settingsDrawable);
        settingsButton.setSize(textureSize, textureSize);
        settingsButton.setVisible(GameState.getState().paused);

        // Pause/resume the game when clicked
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Call the events to pause/resume the game based on the current pause state
                AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                eventHandler.callEvent(GameState.getState().paused ? EventHandler.Event.RESUME_GAME : EventHandler.Event.PAUSE_GAME);
            }
        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                SettingsUI.settingsVisible = true;
            }
        });
        // Place pause button on the table
        table.add(settingsButton).expandX().right().padRight(10);
        table.add(pauseButton).right().padRight(10);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();

        // Assign pause and play events
        eventHandler.createEvent(EventHandler.Event.PAUSE_GAME, (params) -> {
            // Set pause state
            GameState.getState().paused = true;
            // Pause the timer
            MainTimer.getTimerManager().getTimer().pauseTimer();
            // Cancel all actions
            eventHandler.callEvent(EventHandler.Event.CANCEL_OPERATIONS);
            // Disable all UI but the pause button
            InterfaceUtils.disableAllActors(parentTable.getStage());
            InterfaceUtils.enableActor(pauseButton);
            InterfaceUtils.enableActor(settingsButton);
            // Change background to ▶
            pauseButton.getStyle().up = playDrawable;
            pauseButton.getStyle().down = playDrawable;
            settingsButton.setVisible(true);
            return null;
        });
        eventHandler.createEvent(EventHandler.Event.RESUME_GAME, (params) -> {
            // Set pause state
            GameState.getState().paused = false;
            // Resume the timer
            MainTimer.getTimerManager().getTimer().resumeTimer();
            // Re-enable all UI
            InterfaceUtils.enableAllActors(parentTable.getStage());
            // Change background to ❚❚
            pauseButton.getStyle().up = pauseDrawable;
            pauseButton.getStyle().down = pauseDrawable;
            settingsButton.setVisible(false);
            SettingsUI.settingsVisible = false;
            return null;
        });
    }

    public void render() {
        buildMenu.render();
        pauseScreen.render();
    }

    @Override
    public void resize() {
        super.resize();

        buildMenu.resize();
        pauseScreen.resize();
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 60);
        table.setPosition(0, 0);
    }

    @Override
    public void dispose() {
        super.dispose();

        playTexture.dispose();
        pauseTexture.dispose();

        buildMenu.dispose();
        pauseScreen.dispose();
    }
}
