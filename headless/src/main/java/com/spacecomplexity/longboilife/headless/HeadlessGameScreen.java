package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Main;
import com.spacecomplexity.longboilife.MainInputManager;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainCamera;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.game.tile.Tile;
import com.spacecomplexity.longboilife.game.utils.*;
import com.spacecomplexity.longboilife.game.world.World;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Main class to control the game logic.
 */
public class HeadlessGameScreen implements Screen {
    private final Main game;

    private InputManager inputManager;

    private Viewport viewport;

    private World world;

    private final GameState gameState = GameState.getState();

    private float timeSinceScoreUpdate = 0f;
    private float timeSinceMoneyAdded = 0f;

    public HeadlessGameScreen(Main game) {
        this.game = game;

    }

    /**
     * Responsible for setting up the game initial state.
     * Called when the game is first run.
     */
    @Override
    public void show() {

        gameState.reset();

        // Creates a new World object from "map.json" file
        try {
            world = new World(Gdx.files.internal("map.json"));
            gameState.gameWorld = world;
        } catch (FileNotFoundException | InvalidSaveMapException e) {
            throw new RuntimeException(e);
        }

        // Create a new timer for 5 minutes
        MainTimer.getTimerManager().getTimer().setTimer(5 * 60 * 1000);
        MainTimer.getTimerManager().getTimer().setEvent(() -> EventHandler.getEventHandler().callEvent(EventHandler.Event.GAME_END));

        // Create an input multiplexer to handle input from all sources
        InputMultiplexer inputMultiplexer = new InputMultiplexer(new MainInputManager());

        // Initialises camera with CameraManager
        CameraManager camera = new CameraManager(world);
        MainCamera.setMainCamera(camera);

        // Initialise viewport for rescaling
        viewport = new ScreenViewport(MainCamera.camera().getCamera());

        // Calculates the scaling factor based initial screen height
        GameUtils.calculateScaling();

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

        // Initialise the events performed from this script.
        initialiseEvents();
    }

    /**
     * Initialise events for the event handler.
     */
    private void initialiseEvents() {
        EventHandler eventHandler = EventHandler.getEventHandler();

        // Build the selected building
        eventHandler.createEvent(EventHandler.Event.BUILD, (params) -> {
            BuildingType toBuild = gameState.placingBuilding;

            // If there is no selected building do nothing
            if (toBuild == null) {
                return null;
            }

            // If the building is in an invalid location then don't built
            Vector2Int mouse = GameUtils.getMouseOnGrid(world);
            if (!world.canBuild(toBuild, mouse)) {
                return null;
            }

            // If there is no moving building then this is a new build
            if (gameState.movingBuilding == null) {
                // If the user doesn't have enough money to buy the building then don't build
                float cost = toBuild.getCost();
                if (gameState.money < cost) {
                    return null;
                }

                // Build the building at the mouse location and charge the player accordingly
                world.build(toBuild, mouse);
                gameState.money -= cost;

                // Remove the selected building if it is wanted to do so
                if (Arrays.stream(Constants.dontRemoveSelection).noneMatch(category -> gameState.placingBuilding.getCategory() == category) && !gameState.continuousPlacingBuilding) {
                    gameState.placingBuilding = null;
                }
            }
            // If there is a moving building then this is a moved building.
            else {
                // If the user doesn't have enough money to buy the building then don't build
                float cost = toBuild.getCost() * Constants.moveCostRecovery;
                if (gameState.money < cost) {
                    return null;
                }

                // Build the building at the mouse location and charge the player accordingly
                world.build(gameState.movingBuilding, mouse);
                gameState.money -= cost;

                // Remove the old moving building and selected building
                gameState.movingBuilding = null;
                gameState.placingBuilding = null;
            }
            GameUtils.updateSatisfactionScore(world);
            return null;
        });

        // Select a previously built building
        eventHandler.createEvent(EventHandler.Event.SELECT_BUILDING, (params) -> {
            // Get the tile at the mouse coordinates
            Tile tile = world.getTile(GameUtils.getMouseOnGrid(world));
            // Get the building on the tile
            Building selectedBuilding = tile.getBuildingRef();

            // If there is no building here then do nothing
            if (selectedBuilding == null) {
                return null;
            }

            // Set the selected building
            gameState.selectedBuilding = selectedBuilding;

            // Open the selected building menu
            eventHandler.callEvent(EventHandler.Event.OPEN_SELECTED_MENU);
            return null;
        });

        // Cancel all events
        eventHandler.createEvent(EventHandler.Event.CANCEL_OPERATIONS, (params) -> {
            // Close menus and deselect any buildings
            eventHandler.callEvent(EventHandler.Event.CLOSE_BUILD_MENU);
            gameState.placingBuilding = null;
            eventHandler.callEvent(EventHandler.Event.CLOSE_SELECTED_MENU);
            gameState.selectedBuilding = null;

            // If there is a building move in progress cancel this
            if (gameState.movingBuilding != null) {
                world.build(gameState.movingBuilding);
                gameState.movingBuilding = null;
            }

            return null;
        });

        // Sell the selected building
        eventHandler.createEvent(EventHandler.Event.SELL_BUILDING, (params) -> {
            // Delete the building
            world.demolish(gameState.selectedBuilding);
            // Refund the specified amount
            gameState.money += gameState.selectedBuilding.getType().getCost() * Constants.sellCostRecovery;
            // Deselect the removed building
            gameState.selectedBuilding = null;
            GameUtils.updateSatisfactionScore(world);
            return null;
        });

        // Start the move of the selected building
        eventHandler.createEvent(EventHandler.Event.MOVE_BUILDING, (params) -> {
            float cost = gameState.selectedBuilding.getType().getCost() * Constants.moveCostRecovery;
            // If we don't have enough money then don't allow the move
            if (gameState.money < cost) {
                return null;
            }

            // Delete the original building
            world.demolish(gameState.selectedBuilding);
            // Select the same type of building to be placed again
            gameState.placingBuilding = gameState.selectedBuilding.getType();
            // Deselect the removed building and set it to the building to be moved
            gameState.movingBuilding = gameState.selectedBuilding;
            gameState.selectedBuilding = null;

            // Close the menu
            eventHandler.callEvent(EventHandler.Event.CLOSE_SELECTED_MENU);
            GameUtils.updateSatisfactionScore(world);
            return null;
        });

        // Return to the menu
        eventHandler.createEvent(EventHandler.Event.RETURN_MENU, (params) -> {
            game.switchScreen(Main.ScreenType.MENU);

            return null;
        });

        // Event to change screen to the Leaderboard
        eventHandler.createEvent(EventHandler.Event.LEADERBOARD, (params) -> {
            game.switchScreen(Main.ScreenType.LEADERBOARD);

            return null;
        });

        // Returns if there is a buildable tile
        eventHandler.createEvent(EventHandler.Event.ISBUILDABLE, (params) -> {
            for (int x = 0; x < world.getWidth(); x++) {
                for (int y = 0; y < world.getHeight(); y++) {
                    if (world.canBuild(BuildingType.ROAD, new Vector2Int(x, y))) {
                        return true;
                    }
                }
            }
            return false;
        });
    }

    /**
     * Renders the game world, and make calls to handle continuous inputs.
     * Called every frame.
     */
    @Override
    public void render(float delta) {
        // Call to handles any constant input
        inputManager.handleContinuousInput();

        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Applies viewport transformations and updates camera ready for rendering
        viewport.apply();
        MainCamera.camera().update();

        // calls the achievement handler to check for achievements and to ensure the popup is removed
        AchievementHandler.checkAchievements();
        AchievementHandler.updateAchievements();

        // Poll the timer to run the event if the timer has expired
        // Do not update satisfaction score if the game is paused or has ended
        if (!gameState.paused && !MainTimer.getTimerManager().getTimer().poll()) {
            // Update the satisfaction score
            GameUtils.updateVisibleSatisfactionScore();

            // Increase the time in seconds since money and score have been added.
            timeSinceScoreUpdate += delta;
            timeSinceMoneyAdded += delta;

            // Update score to the sum of all accommodation satisfactions.
            if (timeSinceScoreUpdate >= 10) {
                float satisfactionSum = GameUtils.updateSatisfactionScore(world);
                gameState.totalScore += Math.round(satisfactionSum * 100);
                timeSinceScoreUpdate = 0;
            }

            // Add money equal to the sum of the square roots of the costs of accommodations * 100.
            if (timeSinceMoneyAdded >= 5) {
                timeSinceMoneyAdded = 0;
                gameState.money += (world.buildings.stream()
                    .filter(b -> b.getType().getCategory() == BuildingCategory.ACCOMMODATION)
                    .map(building -> ((float) Math.round(Math.sqrt(building.getType().getCost()))))
                    .reduce(0f, Float::sum)
                    * 100);
            }
        }
    }

    /**
     * Handles resizing events, to ensure the game can be scaled.
     * Called when the game window is resized.
     *
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    /**
     * Release all resources held by the game.
     * Called when the game is being closed.
     */
    @Override
    public void dispose() {}
}
