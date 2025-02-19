package com.spacecomplexity.longboilife.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.pathways.PathwayPositions;
import com.spacecomplexity.longboilife.game.pathways.PathwayTextures;
import com.spacecomplexity.longboilife.game.world.World;

import java.util.ArrayList;

/**
 * A class used for rendering utilities.
 */
public class RenderUtils {
    /**
     * Draw the world's base tiles.
     *
     * @param batch the {@link SpriteBatch} to draw sprites to.
     * @param world the {@link World} containing map to draw.
     * @param tint  the tint to apply to all the tiles.
     */
    public static void drawWorld(SpriteBatch batch, World world, Color tint) {
        float cellSize = getCellSize();

        // Begin the batch with the specified tint
        batch.begin();
        batch.setColor(tint);

        // For every tile
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                // Draw the tile texture with size specified by TILE_SIZE and the current scaling factor
                batch.draw(
                    world.getTile(new Vector2Int(x, y)).getType().getTexture(),
                    x * cellSize,
                    y * cellSize,
                    cellSize,
                    cellSize
                );
            }
        }

        // Remove any tints applied
        batch.setColor(Color.WHITE);

        batch.end();
    }

    /**
     * Draw the world's buildings.
     *
     * @param batch the {@link SpriteBatch} to draw sprites to.
     * @param world the {@link World} containing buildings to draw.
     * @param tint  the tint to apply to all the buildings.
     */
    public static void drawBuildings(SpriteBatch batch, World world, Color tint) {
        float cellSize = getCellSize();

        // Begin the batch with the specified tint
        batch.begin();
        batch.setColor(tint);

        // For every building
        for (Building building : world.buildings) {
            Vector2Int buildingPosition = building.getPosition();
            Vector2Int buildingSize = building.getType().getSize();

            Texture texture = building.getType().getTexture();
            float rotation = 0;

            // If the building is a pathway we need to select the correct texture to render.
            if (building.getType().getCategory() == BuildingCategory.PATHWAY) {
                PathwayPositions pathwayPositions = world.getPathwayPosition(buildingPosition);
                texture = PathwayTextures.getTexture(building.getType(), pathwayPositions.getTextureType());
                rotation = pathwayPositions.getRotation();
            }

            // Convert the texture into a texture region for drawing
            TextureRegion textureRegion = new TextureRegion(texture);

            // Draw the building
            batch.draw(
                textureRegion,
                buildingPosition.x * cellSize,
                buildingPosition.y * cellSize,
                (buildingSize.x * cellSize) / 2f, (buildingSize.y * cellSize) / 2f,
                buildingSize.x * cellSize,
                buildingSize.y * cellSize,
                1, 1,
                rotation
            );
        }

        // Remove any tints applied
        batch.setColor(Color.WHITE);

        batch.end();
    }

    /**
     * Draw gridlines between every tile in the world.
     *
     * @param shapeRenderer the {@link ShapeRenderer} to draw lines to.
     * @param world         the {@link World} for size.
     */
    public static void drawWorldGridlines(ShapeRenderer shapeRenderer, World world, Color colour) {
        float cellSize = getCellSize();

        // Begin the shape render with specified colour
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(colour);

        // Draw all vertical lines
        float worldTop = world.getHeight() * cellSize;
        for (int x = 0; x < world.getWidth(); x++) {
            float xEdge = x * cellSize;
            shapeRenderer.line(xEdge, 0, xEdge, worldTop);
        }

        // Draw all horizontal lines
        float worldRight = world.getWidth() * cellSize;
        for (int y = 0; y < world.getHeight(); y++) {
            float yEdge = y * cellSize;
            shapeRenderer.line(0, yEdge, worldRight, yEdge);
        }

        shapeRenderer.end();
    }


    /**
     * Draw a building at the mouse coordinates.
     *
     * @param batch     the {@link SpriteBatch} to draw sprites to.
     * @param world     the {@link World} containing map to check validity.
     * @param building  the building type to draw.
     * @param tint      the tint to apply to the building.
     * @param issueTint the tint to apply to the building if it is invalid.
     */
    public static void drawPlacingBuilding(SpriteBatch batch, World world, BuildingType building, Color tint, Color issueTint) {
        float cellSize = getCellSize();

        Vector2Int mouse = GameUtils.getMouseOnGrid(world);

        // Check if this would be a valid position to build
        boolean validPosition = world.canBuild(building, mouse);

        // Begin the batch with the specified tint based on the building being valid.
        batch.begin();
        if (validPosition) {
            batch.setColor(tint);
        } else {
            batch.setColor(issueTint);
        }

        // Draw the building
        batch.draw(
            building.getTexture(),
            mouse.x * cellSize,
            mouse.y * cellSize,
            building.getSize().x * cellSize,
            building.getSize().y * cellSize
        );

        // Remove any tints applied
        batch.setColor(Color.WHITE);

        batch.end();
    }

    /**
     * Outline a building in the world.
     *
     * @param shapeRenderer the {@link ShapeRenderer} to draw lines to.
     * @param building      the building to outline.
     * @param colour        the colour of the border.
     * @param thickness     the thickness of the border.
     */
    public static void outlineBuilding(ShapeRenderer shapeRenderer, Building building, Color colour, int thickness) {
        float cellSize = getCellSize();

        // Set line thickness directly to GL as LibGdx does not have a way of doing this.
        Gdx.gl.glLineWidth(thickness);
        // Begin the shape render with specified colour
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(colour);

        // Draw a box around the building
        shapeRenderer.rect(
            building.getPosition().x * cellSize,
            building.getPosition().y * cellSize,
            building.getType().getSize().x * cellSize,
            building.getType().getSize().y * cellSize
        );

        shapeRenderer.end();
        // Reset line thickness
        Gdx.gl.glLineWidth(1);
    }

    /**
     * Gets the cell size based on tile size and current scaling factor.
     *
     * @return the cell size (in px).
     */
    private static float getCellSize() {
        GameState gameState = GameState.getState();
        return Constants.TILE_SIZE * gameState.scaleFactor;
    }

    /**
     * Updates the particle emitters and draws the particles onto the world.
     * @param batch current sprite batch
     * @param particles a list of particle spawners
     * @return a list of completed spawners
     */
    public static ArrayList<ParticleSpawner> drawParticles(SpriteBatch batch, ArrayList<ParticleSpawner> particles) {

        // Get time between frames
        float delta = Gdx.graphics.getDeltaTime();
        ArrayList<ParticleSpawner> completedParticles = new ArrayList<>();

        // Update each particle and draw it
        for (ParticleSpawner pe : particles) {
            pe.setEmittersCleanUpBlendFunction(false);

            // Scales each particle if it hasn't been already
            if (!pe.scaled) {
                pe.scaleEffect(0.5f);
                pe.scaled = true;
            }
            pe.update(delta);
            batch.begin();
            pe.draw(batch);
            batch.end();

            // Sets particle to be removed if it has finished playing.
            if (pe.isComplete()) {
                pe.dispose();
                completedParticles.add(pe);
            }
        }
        return completedParticles;
    }
}
