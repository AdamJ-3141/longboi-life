package com.spacecomplexity.longboilife.game.gameevent;

/**
 * Represents a GameEvent in game
 */
public class GameEvent {
    private GameEventType type;

    /**
     * Constructs a game event instance given the specific type.
     *
     * @param type the type of event to create.
     */
    public GameEvent(GameEventType type) {
        this.type = type;
    }

    public GameEventType getType() {
        return type;
    }
}
