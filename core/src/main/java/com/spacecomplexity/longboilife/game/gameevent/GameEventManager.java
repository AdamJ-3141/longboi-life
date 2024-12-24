package com.spacecomplexity.longboilife.game.gameevent;

import java.util.Random;
import java.util.Set;

import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.EventHandler.Event;

/**
 * Class to manage the GameEvents in the game.
 */
public class GameEventManager {
    private static final GameEventManager gameEventManager = new GameEventManager();
    private GameEventTracker tracker;
    private Random random;
    private long lastEventTime;
    private long eventCooldownOffset;
    private static final long EVENTCOOLDOWN = 20 * 1000;
    private static final long EVENTPOPUPTTL = 10 * 1000;
    private static final long MAXOFFSET = 10 * 1000;
    private float satisfactionModifier;

    private GameEventManager() {
        super();
        random = new Random();
        tracker = GameEventTracker.getTracker();
        eventCooldownOffset = 0;
        lastEventTime = Constants.GAME_DURATION;

        satisfactionModifier = 1.0f;
    }

    /**
     *
     * @return the singleton instance of the GameEventManager
     */
    public static GameEventManager getGameEventManager() {
        return gameEventManager;
    }

    public void alterSatisfactionModifier(float offset) {
        satisfactionModifier += offset;
    }

    public float getSatisfactionModifier() {
        return satisfactionModifier;
    }

    /**
     * Tests to see if conditions are correct for an event, and if so will randomly
     * decide if an event happens.
     *
     * @return true if a game event occurs, false otherwise
     */
    public boolean tryForGameEvent() {
        long timeSinceLastEvent = lastEventTime - MainTimer.getTimerManager().getTimer().getTimeLeft();
        if (timeSinceLastEvent > EVENTPOPUPTTL) {
            EventHandler.getEventHandler().callEvent(Event.CLOSE_GAMEEVENT_POPUP,
                    (Object[]) null);
        }
        if (timeSinceLastEvent < EVENTCOOLDOWN + eventCooldownOffset) {
            return false;
        }

        Set<GameEventType> validGameEvents = tracker.findValidEvents();
        GameEventType randomEvent = (GameEventType) validGameEvents.toArray()[random.nextInt(validGameEvents.size())];

        tracker.startGameEvent(randomEvent);
        EventHandler.getEventHandler().callEvent(Event.OPEN_GAMEEVENT_POPUP, randomEvent);

        lastEventTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        eventCooldownOffset = random.nextLong(MAXOFFSET);
        return true;
    }
}
