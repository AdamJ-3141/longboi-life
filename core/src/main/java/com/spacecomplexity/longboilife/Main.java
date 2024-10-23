package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.building.BuildingType;
import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.ui.UIManager;
import com.spacecomplexity.longboilife.utils.GameUtils;
import com.spacecomplexity.longboilife.utils.RenderUtils;
import com.spacecomplexity.longboilife.utils.Vector2Int;
import com.spacecomplexity.longboilife.world.World;

import java.io.FileNotFoundException;


/**
 * The main class (entry point) for game logic.
 * Responsible for handling rendering and input.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private UIManager ui;
    private InputManager inputManager;

    private Viewport viewport;

    private World world;

    private final GameState gameState = GameState.getState();

    /**
     * Responsible for setting up the game initial state.
     * Called when the game is first run.
     */
    @Override
    public void create() {
        // Creates a new World object from "map.json" file
        try {
            world = new World(Gdx.files.internal("map.json"));
        } catch (FileNotFoundException | InvalidSaveMapException e) {
            throw new RuntimeException(e);
        }

        // Create a new timer for 5 minutes
        TimerManager.getTimerManager().getTimer().setTimer(5 * 60 * 1000);

        // Create an input multiplexer to handle input from all sources
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        // Initialise SpriteBatch and ShapeRender for rendering
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Initialises camera with CameraManager
        CameraManager camera = new CameraManager(world);
        MainCamera.setMainCamera(camera);

        // Initialise viewport for rescaling
        viewport = new ScreenViewport(MainCamera.camera().getCamera());

        // Calculates the scaling factor based initial screen height
        GameUtils.calculateScaling();

        // Initialise UI elements with UIManager
        ui = new UIManager(inputMultiplexer);

        // Position camera in the center of the world map
        MainCamera.camera().position.set(new Vector3(
            world.getWidth() * Constants.TILE_SIZE * gameState.scaleFactor / 2,
            world.getHeight() * Constants.TILE_SIZE * gameState.scaleFactor / 2,
            0
        ));

        // Set up an InputManager to handle user inputs
        inputManager = new InputManager(inputMultiplexer);
        // Set the Gdx input processor to handle all our input processes
        Gdx.input.setInputProcessor(inputMultiplexer);

        // todo why do i need this here + when full screening, create the same issue
        // Initialise the events performed from this script.
        initialiseEvents();
    }

    /**
     * Initialise events for the event handler.
     */
    private void initialiseEvents() {
        // Build the selected building
        EventHandler.getEventHandler().createEvent("build", (params) -> {
            BuildingType toBuild = gameState.selectedBuilding;

            // If there is no selected building do nothing
            if (toBuild == null) {
                return null;
            }

            // If the user doesn't have enough money to buy the building then don't build
            float cost = toBuild.getCost();
            if (gameState.money < cost) {
                return null;
            }

            // If the building is in an invalid location then don't built
            Vector2Int mouse = GameUtils.getMouseOnGrid(world);
            if (!world.canBuild(toBuild, mouse.x, mouse.y)) {
                return null;
            }

            // Build the building at the mouse location and charge the player accordingly
            world.build(toBuild, mouse.x, mouse.y);
            gameState.money -= cost;

            // Remove the selected building
            gameState.selectedBuilding = null;

            return null;
        });
    }

    /**
     * Renders the game world, and make calls to handle continuous inputs.
     * Called every frame.
     */
    @Override
    public void render() {
        // Call to handles any constant input
        inputManager.handleContinuousInput();

        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Applies viewport transformations and updates camera ready for rendering
        viewport.apply();
        MainCamera.camera().update();
        // Update the SpriteBatch and ShapeRenderer to match the updates camera
        batch.setProjectionMatrix(MainCamera.camera().getCombinedMatrix());
        shapeRenderer.setProjectionMatrix(MainCamera.camera().getCombinedMatrix());

        // Draw the world on screen
        RenderUtils.drawWorld(batch, shapeRenderer, world, gameState.selectedBuilding, gameState.selectedBuilding != null);

        // Render the UI
        ui.render();
    }

    /**
     * Handles resizing events, to ensure the game can be scaled.
     * Called when the game window is resized.
     *
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        // Updates viewport to match new window size
        viewport.update(width, height, false);

        // Recalculate scaling factors with new height
        GameUtils.calculateScaling();

        // Rescale UI
        ui.resize(width, height);
    }

    /**
     * Release all resources held by the game.
     * Called when the game is being closed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        ui.dispose();
    }
}
