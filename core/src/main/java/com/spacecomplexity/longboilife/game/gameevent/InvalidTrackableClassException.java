package com.spacecomplexity.longboilife.game.gameevent;

/**
 * Thrown when attempting to track Game Event data of a type that is different
 * to the type allowed by the associated GameEventTrackable
 */
public class InvalidTrackableClassException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidTrackableClassException} with no reason
     */
    public InvalidTrackableClassException() {
        super();
    }

    /**
     * Constructs a new {@code InvalidTrackableClassException} with a given reason
     *
     * @param reason - The reason for throwing this exception
     */
    public InvalidTrackableClassException(String reason) {
        super(reason);
    }
}
