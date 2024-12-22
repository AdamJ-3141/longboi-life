package com.spacecomplexity.longboilife.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.audio.AudioController;
import com.spacecomplexity.longboilife.game.audio.GameMusic;
import com.spacecomplexity.longboilife.menu.MenuState;

public class UIMusicInfo extends UIElement{

    private final Image coverImage;
    private final Label titleLabel;
    private final Label composerLabel;

    /**
     * Initialise base UI elements
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIMusicInfo(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        coverImage = new Image();
        Table musicInfoTable = new Table();
        titleLabel = new Label("", skin);
        titleLabel.setFontScale(1f);
        composerLabel = new Label("", skin);
        composerLabel.setFontScale(0.8f);

        musicInfoTable.add(titleLabel);
        musicInfoTable.row();
        musicInfoTable.add(composerLabel);
        table.add(coverImage).width(25).height(25).padRight(20);
        table.add(musicInfoTable).padRight(20);

        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(200, 50);
    }

    /**
     * Render this UI element.
     */
    @Override
    public void render() {
        GameMusic music = AudioController.getInstance().getCurrentPlaylist().getCurrentMusic();
        coverImage.setDrawable(music.coverDrawable);
        titleLabel.setText(music.title);
        composerLabel.setText(music.composer);
        placeTable();
    }

    /**
     * Set the table position relative to the viewport.
     */
    @Override
    protected void placeTable() {
        if (MenuState.inMenu) {
            table.setPosition(80, 0);
        }
        else {
            table.setPosition(70, uiViewport.getWorldHeight() - table.getHeight());
        }
    }
}
