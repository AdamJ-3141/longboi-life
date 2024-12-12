package com.spacecomplexity.longboilife.game.achievement;

import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

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
    TENBUILDING("Ten buildings", 1000) {
        /**
         * checks if more than 10 buildings have been placed
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount() >= 10;
        }
    },
    FULLMAP("Fill the map up", 1000000) {
        /**
         * checks if there are any buildable tiles on the map
         * @return  true if so
         */
        public boolean isAchieved() {
            return !(boolean) EventHandler.getEventHandler().callEvent(EventHandler.Event.ISBUILDABLE);
        }
    },
    FIVEGREGGS("Build 5 Greggs", 10000) {
        /**
         * checks if there are any buildable tiles on the map
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.GREGGS) >=5;
        }
    },
    TWOHUNDREDROAD("Highway to Hell", 100000) {
        /**
         * checks if 300 roads have been built
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.ROAD) >=300;
        }
    },
    ONEOFEACH("One of each building", 1000) {
        /**
         * checks if 1 of each building is built
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.GREGGS) >=1 &&
                    GameState.getState().getBuildingCount(BuildingType.GYM) >=1 &&
                    GameState.getState().getBuildingCount(BuildingType.HALLS) >=1 &&
                    GameState.getState().getBuildingCount(BuildingType.LIBRARY) >=1;
        }
    },
    FIVEOFEACH("Five of each building", 100000) {
        /**
         * checks if 5 of each building is built
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.GREGGS) >=5 &&
                    GameState.getState().getBuildingCount(BuildingType.GYM) >=5 &&
                    GameState.getState().getBuildingCount(BuildingType.HALLS) >=5 &&
                    GameState.getState().getBuildingCount(BuildingType.LIBRARY) >=5;
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
