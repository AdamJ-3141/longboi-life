package com.spacecomplexity.longboilife.game.gameevent;

/**
 * Represents the types of data that can be tracked by the GameEventTracker.
 * Each Trackable has an expected data type that data associated with that
 * trackable must conform to
 */
public enum GameEventTrackable {
    /**
     * The cost of the last building destroyed by fire. Expects {@code float}
     */
    FIRE_BUILDING_COST(Float.class),
    /**
     * The next fire insurance event that should occur, decided by the last fire.
     * Expects {@link GameEventType}
     */
    NEXT_FIRE_INSURANCE(GameEventType.class);

    private final Class<?> expectedClass;

    GameEventTrackable(Class<?> expectedClass) {
        this.expectedClass = expectedClass;
    }

    /**
     *
     * @return the class expected by the GameEventTrackable
     */
    public Class<? extends Object> getExpectedClass() {
        return expectedClass;
    }

}
