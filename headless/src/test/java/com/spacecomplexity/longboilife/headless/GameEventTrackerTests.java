package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.game.gameevent.GameEventTrackable;
import com.spacecomplexity.longboilife.game.gameevent.GameEventTracker;
import com.spacecomplexity.longboilife.game.gameevent.GameEventType;
import com.spacecomplexity.longboilife.game.gameevent.InvalidTrackableClassException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameEventTrackerTests extends AbstractHeadlessGdxTest {
    GameEventTracker tracker;

    @BeforeEach
    public void getTracker() {
        tracker = GameEventTracker.getTracker();
    }

    @Test
    public void startGameEventAddsToActiveTest() {
        Set<GameEventType> testSet = new HashSet<GameEventType>();
        assertEquals(testSet, tracker.getActiveGameEvents());
        testSet.add(GameEventType.CELEBRITY);
        tracker.startGameEvent(GameEventType.CELEBRITY);
        assertEquals(testSet, tracker.getActiveGameEvents(),
                "Did not add the GameEvent successfully");
    }

    @Test
    public void endGameEventRemovesFromActiveTest() {
        Set<GameEventType> testSet = new HashSet<GameEventType>();
        assertEquals(testSet, tracker.getActiveGameEvents());
        tracker.startGameEvent(GameEventType.CELEBRITY);
        tracker.endGameEvent(GameEventType.CELEBRITY);
        assertEquals(testSet, tracker.getActiveGameEvents(),
                "Did not remove the GameEvent successfully");
    }

    @Test
    public void endGameEventAddsToPassiveTest() {
        Map<GameEventType, Long> testMap = new HashMap<GameEventType, Long>();
        Set<GameEventType> testSet = new HashSet<GameEventType>();
        assertEquals(testMap, tracker.getPassedGameEvents());

        testSet.add(GameEventType.CELEBRITY);
        tracker.startGameEvent(GameEventType.CELEBRITY);
        tracker.endGameEvent(GameEventType.CELEBRITY);
        assertEquals(testSet, tracker.getPassedGameEvents().keySet(),
                "Did not remove the GameEvent successfully");
        assertTrue(
                tracker.getPassedGameEvents().get(GameEventType.CELEBRITY)
                        .getClass() == Long.class,
                "Did not remove the GameEvent successfully");
    }

    @Test
    public void findValidEventsTest() {
        // By default, we expect some events to start as valid, including
        // GameEventType.CELEBRITY
        assertTrue(tracker.findValidEvents().contains(GameEventType.CELEBRITY));
    }

    @Test
    public void trackDataValidTypeTest() {
        final float TEST_VALUE = 5f;
        assertNull(tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST));
        tracker.trackData(GameEventTrackable.FIRE_BUILDING_COST, TEST_VALUE);
        assertEquals(TEST_VALUE, tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST));
    }

    @Test
    public void trackDataInvalidTypeTest() {
        final String TEST_VALUE = "Hello";
        assertNull(tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST));
        assertThrows(InvalidTrackableClassException.class, () -> {
            tracker.trackData(GameEventTrackable.FIRE_BUILDING_COST, TEST_VALUE);
        });
    }

    @Test
    public void retreiveDataNoDataTest() {
        assertNull(tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST));
    }

    @Test
    public void retreiveDataGoodDataTest() {
        final float TEST_VALUE = 5f;
        tracker.trackData(GameEventTrackable.FIRE_BUILDING_COST, TEST_VALUE);
        assertEquals(TEST_VALUE, tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST));
    }

    @Test
    public void ResetTrackerTest() {
        final float TEST_VALUE_FLOAT = 5f;
        tracker.trackData(GameEventTrackable.FIRE_BUILDING_COST, TEST_VALUE_FLOAT);
        tracker.startGameEvent(GameEventType.CELEBRITY);
        tracker.endGameEvent(GameEventType.CELEBRITY);
        tracker.startGameEvent(GameEventType.LONGBOI);
        tracker.reset();
        assertEquals(new HashSet<GameEventType>(), tracker.getActiveGameEvents(),
                "ActiveGameEvents was not cleared on reset");
        assertEquals(new HashMap<GameEventType, Long>(), tracker.getPassedGameEvents(),
                "PassedGameEvents was not cleared on reset");
        assertNull(tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST), "TrackedData was not cleared on reset");
    }

}
