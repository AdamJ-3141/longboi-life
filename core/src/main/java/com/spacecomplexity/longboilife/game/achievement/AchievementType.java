package com.spacecomplexity.longboilife.game.achievement;

import com.spacecomplexity.longboilife.game.globals.GameState;

public enum AchievementType {
    FIRSTBUILDING("First building", 100) {
        /**
         * checks if more than one building has been placed
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount() >= 1;
        }
    },
    TENBUILDING("Ten building", 100) {
        /**
         * checks if more than 10 buildings have been placed
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount() >= 10;
        }
    },
    ;

    /**
     * checks if the achievement condition has been forfilled
     * @return  true if achievement is complete otherwise false
     */
    public abstract boolean isAchieved();

    public final String displayName;
    public final int scoreChange;

    /**
     * creates an Achievement with predetermined attributes
     * @param displayName   Text to be displayed when the achievement is completed
     * @param scoreChange   Amount by which the score will be impacted if this achievement succeeds
     */
    AchievementType(String displayName, int scoreChange) {
        this.displayName = displayName;
        this.scoreChange = scoreChange;
    }
}
