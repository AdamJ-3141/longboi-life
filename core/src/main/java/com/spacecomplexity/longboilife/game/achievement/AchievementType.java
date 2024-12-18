package com.spacecomplexity.longboilife.game.achievement;

import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.EventHandler;

import java.util.ArrayList;

public enum AchievementType {
    FIRSTBUILDING("First building", "Build one building", 100) {
        /**
         * checks if more than one building has been placed
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount() >= 1;
        }
    },
    TENBUILDING("Ten buildings", "Build ten buildings", 1000) {
        /**
         * checks if more than 10 buildings have been placed
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount() >= 10;
        }
    },
    FIFTYBUILDING("Mega Uni", "Build fifty buildings", 5000) {
        /**
         * checks if more than 10 buildings have been placed
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount() >= 10;
        }
    },
    FULLMAP("Full house", "Fill all tiles on the map", 10000) {
        /**
         * checks if there are any buildable tiles on the map
         * @return  true if so
         */
        public boolean isAchieved() {
            return !(boolean) EventHandler.getEventHandler().callEvent(EventHandler.Event.ISBUILDABLE);
        }
    },
    FIVEGREGGS("Greggopolis","Build 5 Greggs", 1000) {
        /**
         * checks if there are any buildable tiles on the map
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.GREGGS) >=5;
        }
    },
    THREEHUNDREDROAD("Highway to Hell","Build 300 roads", 3000) {
        /**
         * checks if 300 roads have been built
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().getBuildingCount(BuildingType.ROAD) >=300;
        }
    },
    ONEOFEACH("Got to get them all","Build one of each building", 2000) {
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
    FIVEOFEACH("Too Many Buildings!!","Build 5 of each building", 5000) {
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
    MILLIONAIRE("Millionaire","Reach 1 million currency", 5000) {
        /**
         * checks the player has more than 1,000,000 currency
         * @return  true if so
         */
        public boolean isAchieved() {
            return GameState.getState().money >= 1000000;
        }
    },

    ;

    /**
     * checks if the achievement condition has been forfilled
     * @return  true if achievement is complete otherwise false
     */
    public abstract boolean isAchieved();

    public final String title;
    public final String description;
    public final int scoreChange;

    /**
     * creates an Achievement with predetermined attributes
     * @param title   Text to be displayed when the achievement is completed
     * @param description Text to be displayed explaining the achievement
     * @param scoreChange   Amount by which the score will be impacted if this achievement succeeds
     */
    AchievementType(String title, String description, int scoreChange) {
        this.title = title;
        this.description = description;
        this.scoreChange = scoreChange;
    }
}
