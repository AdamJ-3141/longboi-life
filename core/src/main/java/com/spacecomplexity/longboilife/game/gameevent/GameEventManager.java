package com.spacecomplexity.longboilife.game.gameevent;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

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
    private GameEventType nextEvent;
    // The following are times in milliseconds
    private static final long EVENTCOOLDOWN = 20 * 1000;
    private static final long EVENTPOPUPTTL = 10 * 1000;
    private static final long MAXOFFSET = 10 * 1000;

    private GameEventManager() {
        super();
        random = new Random();
        tracker = GameEventTracker.getTracker();
        eventCooldownOffset = 0;
        lastEventTime = Constants.GAME_DURATION;
    }

    /**
     * @return the singleton instance of the GameEventManager
     */
    public static GameEventManager getGameEventManager() {
        return gameEventManager;
    }

    /**
     * Execute the logic for all ongoing events
     */
    public void tickOngoingEvents() {
        GameEventType[] activeEvents = tracker.getActiveGameEvents().toArray(new GameEventType[0]);
        for (GameEventType activeEvent : activeEvents) {
            activeEvent.ongoingEffect();
        }
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
        // If some events occur, they allow other events to occur, and some of these
        // should happen immediately after
        Set<GameEventType> urgentGameEvents = validGameEvents.stream()
                .filter(s -> s.isUrgent())
                .collect(Collectors.toSet());

        nextEvent = null;
        if (!urgentGameEvents.isEmpty()) {
            nextEvent = (GameEventType) urgentGameEvents.toArray()[random.nextInt(urgentGameEvents.size())];
        } else if (!validGameEvents.isEmpty()) {
            nextEvent = (GameEventType) validGameEvents.toArray()[random.nextInt(validGameEvents.size())];
        } else {
            return false;
        }

        tracker.startGameEvent(nextEvent);
        EventHandler.getEventHandler().callEvent(Event.OPEN_GAMEEVENT_POPUP, nextEvent);

        lastEventTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        eventCooldownOffset = random.nextLong(MAXOFFSET);
        return true;
    }
}
