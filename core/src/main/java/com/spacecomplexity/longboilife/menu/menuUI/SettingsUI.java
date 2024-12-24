package com.spacecomplexity.longboilife.menu.menuUI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.SoundEffect;
import com.spacecomplexity.longboilife.game.globals.Keybindings;
import com.spacecomplexity.longboilife.game.ui.UIElement;
import com.spacecomplexity.longboilife.game.utils.InterfaceUtils;


import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Class that uses UIElement abstract class to create a display for the settings menu
 */
public class SettingsUI extends UIElement {

    private final Slider musicVolumeSlider;
    private final Slider soundVolumeSlider;

    public static boolean settingsVisible = false;
    public static SettingsUI settings;

    /**
     * Initialise settings window.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public SettingsUI(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);
        settings = this;  // this is clunky, but I can't think of any other way to do it.

        // Create UI components.
        Label title = new Label("Settings", skin);
        title.setAlignment(Align.center);
        title.setFontScale(2f);
        Label audioLabel = new Label("Sound Volume", skin);
        audioLabel.setAlignment(Align.center);
        audioLabel.setFontScale(1.5f);
        Label musicVolumeLabel = new Label("Music", skin);
        musicVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        musicVolumeSlider.setValue(AudioController.musicVolume);
        Label soundVolumeLabel = new Label("Sound Effects", skin);
        soundVolumeSlider = new Slider(0f, 1f, 0.05f, false, skin);
        soundVolumeSlider.setValue(AudioController.soundVolume);
        Label keybindsLabel = new Label("Keybinds", skin);
        keybindsLabel.setAlignment(Align.center);
        keybindsLabel.setFontScale(1.5f);

        // Get all the keybindings.
        Label keybindsNameLabel = new Label(
            Arrays.stream(Keybindings.values()).map(Keybindings::getDisplayName).collect(Collectors.joining("\n")),
            skin);
        keybindsNameLabel.setColor(Color.CYAN);
        Label keybindsKeyLabel = new Label(
            Arrays.stream(Keybindings.values()).map(Keybindings::getKey).map(Input.Keys::toString).collect(Collectors.joining("\n")),
            skin);
        Label keybindsMouseLabel = new Label(
            Arrays.stream(Keybindings.values()).map(Keybindings::getMouse).collect(Collectors.joining("\n")),
            skin);

        // Initialise exit button
        TextButton exitButton = new TextButton("Back", skin, "round");
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Close settings
                AudioController.getInstance().playSound(SoundEffect.BUTTON_CLICK);
                settingsVisible = false;
            }
        });

        // Place elements onto table
        Table elementsTable = new Table(skin);

        elementsTable.add(audioLabel).colspan(3);
        elementsTable.row();
        elementsTable.add(musicVolumeLabel).right().padRight(10);
        elementsTable.add(musicVolumeSlider).colspan(2).left().padLeft(10);
        elementsTable.row();
        elementsTable.add(soundVolumeLabel).right().padRight(10);
        elementsTable.add(soundVolumeSlider).colspan(2).left().padLeft(10);
        elementsTable.row();
        elementsTable.add(keybindsLabel).colspan(3).padTop(10);
        elementsTable.row();
        elementsTable.add(keybindsNameLabel).padRight(10);
        elementsTable.add(keybindsKeyLabel).padRight(10);
        elementsTable.add(keybindsMouseLabel);

        table.add(title).fillY().align(Align.center).padBottom(20);
        table.row();
        table.add(elementsTable).fillX().padBottom(20);
        table.row();
        table.add(exitButton).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(400, 400);
        placeTable();
    }

    /**
     * Renders the settings window, updates the text if its currently being displayed
     */
    public void render() {
        placeTable();
        if (settingsVisible) {
            settings = this;
            InterfaceUtils.enableChildren(table);
            AudioController.musicVolume = musicVolumeSlider.getValue();
            AudioController.soundVolume = soundVolumeSlider.getValue();
        }
    }

    public static Table getTable() {
        return settings.table;
    }

    /**
     * places table on screen if the settings window is currently being displayed
     */
    @Override
    protected void placeTable() {
        if (settingsVisible) {
            table.setPosition(
                (uiViewport.getWorldWidth() - table.getWidth()) / 2,
                (uiViewport.getWorldHeight() - table.getHeight()) / 2);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
