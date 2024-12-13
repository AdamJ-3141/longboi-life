package com.spacecomplexity.longboilife.game.achievement;

import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

import java.util.ArrayList;

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
    FULLMAP("Fill the map up", 20000) {
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
    THREEHUNDREDROAD("Highway to Hell", 50000) {
        /**
         * checks if 300 roads have been built
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.ROAD) >=300;
        }
    },
    ONEOFEACH("One of each building", 2000) {
        /**
         * checks if 1 of each building is built
         * @return  true if so
         */
        public boolean isAchieved() {
            BuildingType[] buildingTypes = BuildingType.values();
            ArrayList<Boolean> placedMoreThanOne = new ArrayList<>();
            for (BuildingType buildingType : buildingTypes) {
                if (buildingType.getCategory() != BuildingCategory.PATHWAY) {
                    placedMoreThanOne.add(GameState.getState().getBuildingCount(buildingType) >= 1);
                }
            }
            return !placedMoreThanOne.contains(false);
        }
    },
    FIVEOFEACH("Five of each building", 10000) {
        /**
         * checks if 5 of each building is built
         * @return  true if so
         */
        public boolean isAchieved() {
            BuildingType[] buildingTypes = BuildingType.values();
            ArrayList<Boolean> placedMoreThanFive = new ArrayList<>();
            for (BuildingType buildingType : buildingTypes) {
                if (buildingType.getCategory() != BuildingCategory.PATHWAY) {
                    placedMoreThanFive.add(GameState.getState().getBuildingCount(buildingType) >=5);
                }
            }
            return !placedMoreThanFive.contains(false);
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
