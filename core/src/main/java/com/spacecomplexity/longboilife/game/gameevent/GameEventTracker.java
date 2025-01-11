package com.spacecomplexity.longboilife.game.gameevent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.spacecomplexity.longboilife.game.globals.MainTimer;

/**
 * Keeps track of game event status and data
 */
public class GameEventTracker {
    private static final GameEventTracker gameEventTracker = new GameEventTracker();

    private Set<GameEventType> activeGameEvents;
    // We keep track of the last time a particular game event ended
    private Map<GameEventType, Long> passedGameEvents;
    private Map<GameEventTrackable, Object> trackedData;

    private GameEventTracker() {
        activeGameEvents = new HashSet<>();
        passedGameEvents = new HashMap<>();
        trackedData = new HashMap<>();
    }

    /**
     * @return the singleton instance of the GameEventTracker
     */
    public static GameEventTracker getTracker() {
        return gameEventTracker;
    }

    /**
     * Add a game event to the set of active {@link GameEventType} and execute its
     * start effect
     *
     * @param gameEvent the Game Event to start
     */
    public void startGameEvent(GameEventType gameEvent) {
        activeGameEvents.add(gameEvent);
        gameEvent.startEffect();
    }

    /**
     * If a game event is in the set of active {@link GameEventType}, removes it,
     * records the time that the game event ended, and executes its end effect.
     * Otherwise, do nothing
     *
     * @param gameEvent the Game Event to end
     */
    public void endGameEvent(GameEventType gameEvent) {
        if (!activeGameEvents.contains(gameEvent)) {
            return;
        }
        activeGameEvents.remove(gameEvent);
        passedGameEvents.put(gameEvent, MainTimer.getTimerManager().getTimer().getTimeLeft());
        gameEvent.endEffect();
    }

    /**
     *
     * @return the set of active (ongoing) {@link GameEventType}
     */
    public Set<GameEventType> getActiveGameEvents() {
        return activeGameEvents;
    }

    /**
     * A mapping of passed game events and the time that they most recently finished
     *
     * @return the map of passed (expired) {@link GameEventType}
     */
    public Map<GameEventType, Long> getPassedGameEvents() {
        return passedGameEvents;
    }

    /**
     *
     * @return the set of {@link GameEventType} that can be started
     */
    public Set<GameEventType> findValidEvents() {
        Set<GameEventType> validGameEvents = new HashSet<>();
        for (GameEventType gameEventType : GameEventType.values()) {
            if (gameEventType.isValid())
                validGameEvents.add(gameEventType);
        }
        return validGameEvents;
    }

    /**
     * Allows a request for the tracker to store data associated with a
     * {@link GameEventTrackable}. Acts as a wrapper for
     * {@code Map<GameEventTrackable, Object>}
     *
     * @param trackable - the tag of the trackable
     * @param data      - the data to be associated with the trackable. Must comply
     *                  with the trackable's expected type
     *
     * @return the previous value associated with this trackable tag, if it exists
     */
    public Object trackData(GameEventTrackable trackable, Object data) {
        if (!trackable.getExpectedClass().isInstance(data)) {
            throw new InvalidTrackableClassException("Expected data of type " + trackable.getExpectedClass().toString()
                    + " but got data of type " + data.toString());
        }
        return trackedData.put(trackable, data);
    }

    /**
     * Allows retreival of a value associated with a trackable
     *
     * @param trackable - the tag of the trackable to query
     *
     * @return the value associated with this trackable
     */
    public Object retreiveData(GameEventTrackable trackable) {
        return trackedData.get(trackable);
    }

    public void reset() {
        activeGameEvents.clear();
        passedGameEvents.clear();
        trackedData.clear();
    }
}
