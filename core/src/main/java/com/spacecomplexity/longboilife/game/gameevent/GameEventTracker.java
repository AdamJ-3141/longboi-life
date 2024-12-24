package com.spacecomplexity.longboilife.game.gameevent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.spacecomplexity.longboilife.game.globals.MainTimer;

public class GameEventTracker {
    private static final GameEventTracker gameEventTracker = new GameEventTracker();

    private Set<GameEventType> activeGameEvents;
    // We keep track of the last time a particular game event ended
    private Map<GameEventType, Long> passedGameEvents;

    private GameEventTracker() {
        activeGameEvents = new HashSet<>();
        passedGameEvents = new HashMap<>();
    }

    public static GameEventTracker getTracker() {
        return gameEventTracker;
    }

    public void startGameEvent(GameEventType gameEvent) {
        activeGameEvents.add(gameEvent);
        gameEvent.startEffect();
    }

    public void endGameEvent(GameEventType gameEvent) {
        if (!activeGameEvents.contains(gameEvent)) {
            return;
        }
        activeGameEvents.remove(gameEvent);
        passedGameEvents.put(gameEvent, MainTimer.getTimerManager().getTimer().getTimeLeft());
        gameEvent.endEffect();
    }

    public Set<GameEventType> getActiveGameEvents() {
        return activeGameEvents;
    }

    public Map<GameEventType, Long> getPassedGameEvents() {
        return passedGameEvents;
    }

    public Set<GameEventType> findValidEvents() {
        Set<GameEventType> validGameEvents = new HashSet<>();
        for (GameEventType gameEventType : GameEventType.values()) {
            if (gameEventType.isValid())
                validGameEvents.add(gameEventType);
        }
        return validGameEvents;
    }
}
