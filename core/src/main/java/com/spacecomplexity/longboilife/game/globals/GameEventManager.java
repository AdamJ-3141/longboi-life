package com.spacecomplexity.longboilife.game.globals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.spacecomplexity.longboilife.game.gameevent.GameEventType;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

/**
 * Class to manage the GameEvents in the game.
 */
public class GameEventManager {
    private static final GameEventManager gameEventManager = new GameEventManager();

    Random random;
    GameEventType currentGameEvent;
    private long lastGameEventTime;
    private int gameEventChance;
    ArrayList<GameEventType> gameEventBag;

    private GameEventManager() {
        super();
        this.random = new Random();
        lastGameEventTime = Constants.GAME_DURATION;
        gameEventBag = new ArrayList<>(List.of(GameEventType.values()));
    }

    /**
     *
     * @return the singleton instance of the GameEventManager
     */
    public static GameEventManager getGameEventManager() {
        return gameEventManager;
    }

    public GameEventType getCurrentGameEvent() {
        return currentGameEvent;
    }

    /**
     * Tests to see if conditions are correct for an event, and if so will randomly
     * decide if an event happens.
     *
     * @return True if a game event occurs
     */
    public boolean tryForGameEvent() {
        // If insufficient time has passed since the last event, return false
        if (lastGameEventTime
                - MainTimer.getTimerManager().getTimer().getTimeLeft() < Constants.MIN_GAME_EVENT_INTERVAL) {
            return false;
        }

        // Events should occur at a random duration after the interval, the likelihood
        // of an event happening increases each time an event fails to occur
        if (random.nextInt(3) >= gameEventChance) {
            gameEventChance++;
            return false;
        }

        gameEventChance = 0;
        currentGameEvent = rollGameEvent();
        lastGameEventTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        EventHandler.getEventHandler().callEvent(EventHandler.Event.OPEN_GAMEEVENT_POPUP);
        // TODO: Increase score
        return true;
    }

    /**
     * Rolls and returns a random event
     */
    public GameEventType rollGameEvent() {
        // TODO: Make events weighted? Chosen in a more interesting manner?

        /*
         * A bag shuffle is used to make it so that events aren't repeated until all
         * events have been seen
         */
        if (gameEventBag.size() == 0) {
            gameEventBag.addAll(List.of(GameEventType.values()));
        }
        return gameEventBag.remove(random.nextInt(gameEventBag.size()));
    }
}
