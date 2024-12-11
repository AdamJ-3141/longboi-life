package com.spacecomplexity.longboilife.game.gameevent;

public enum GameEventType {
    GRADUATION("Graduation", 20, "It's graduation day"),
    CELEBRITY("Celebrity visit", 5, "A celebrity is visiting the university");

    private final String displayName;
    private final float scoreEffect;
    private final String eventMessage;

    /**
     * Creates a {@link GameEventType} with specified attributes
     *
     * @param displayName
     * @param scoreEffect
     */
    GameEventType(String displayName, float scoreEffect, String eventMessage) {
        this.displayName = displayName;
        this.scoreEffect = scoreEffect;
        this.eventMessage = eventMessage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public float getScoreEffect() {
        return scoreEffect;
    }

    public String getEventMessage() {
        return eventMessage;
    }
}
