package com.spacecomplexity.longboilife.game.ui.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.ui.UIElement;

import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.UIUtils;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import com.spacecomplexity.longboilife.game.world.World;
import com.spacecomplexity.longboilife.game.utils.GameUtils;

public class UIAccomSatisfactionTooltip extends UIElement {

    private final World world;
    private Boolean hovering = false;
    private final Label satisfactionLabel;

    public UIAccomSatisfactionTooltip(Viewport uiViewport, Table parentTable, Skin skin, World world) {
        super(uiViewport, parentTable, skin);
        satisfactionLabel = new Label(null, skin);
        this.world = world;
        Label titleLabel = new Label("Satisfaction:", skin);
        titleLabel.setFontScale(1f);

        satisfactionLabel.setFontScale(1f);

        table.add(titleLabel);
        table.row();
        table.add(satisfactionLabel);

        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(100, 50);
        placeTable();

    }

    @Override
    public void render() {

        GameState gameState = GameState.getState();
        Vector2Int mouse = GameUtils.getMouseOnGrid(world);
        Building building = world.getTile(mouse).getBuildingRef();
        if (building != null && building.getType().getCategory() == BuildingCategory.ACCOMMODATION) {
            hovering = true;
            float satisfaction = gameState.accomSatisfaction.get(building);
            satisfactionLabel.setText(String.format("%.2f%%", satisfaction * 100));
            satisfactionLabel.setColor(
                satisfaction >= 1 ? Color.GREEN :
                    satisfaction >= 0.7 ? Color.YELLOW :
                        satisfaction >= 0.4 ? Color.ORANGE :
                            Color.RED
                );
        } else {
            hovering = false;
        }

        placeTable();
    }

    /**
     * Places table off-screen if not hovering over accommodation.
     */
    @Override
    protected void placeTable() {
        if (hovering) {
            Vector2 mouse = UIUtils.getMousePositionInViewport(uiViewport);
            table.setPosition(mouse.x, mouse.y);
        }
        else {
            table.setPosition(uiViewport.getWorldWidth(), uiViewport.getWorldHeight());
        }
    }
}
