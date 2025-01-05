package com.spacecomplexity.longboilife.game.gameevent;

import com.badlogic.gdx.graphics.Color;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.GameState;

/**
 * Represents the different Game Events that can occur during gameplay.
 */
public enum GameEventType {
    CELEBRITY(
            "Celebrity Visit",
            "A celebrity is visiting the university",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    DISEASE(
            "Fresher's Flu",
            "Fresher's flu runs rampant",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    LONGBOI(
            "Feathery Friend",
            "A notable duck on campus is gaining fame",
            Color.GREEN) {
        private final float MODIFIER = 0.1f;

        @Override
        public boolean isValid() {
            return !GameEventTracker.getTracker().getPassedGameEvents().containsKey(LONGBOI);
        }

        @Override
        public void startEffect() {
            // 10% satisfaction bonus
        }

        @Override
        public void endEffect() {
        }
    },
    DEADBOI(
            "Duck's Demise",
            "Disaster! The famous duck has passed away",
            Color.RED) {
        @Override
        public boolean isValid() {
            return GameEventTracker.getTracker().getActiveGameEvents().contains(LONGBOI);
        }

        @Override
        public void startEffect() {
            GameEventTracker.getTracker().endGameEvent(LONGBOI);
            // Self-ending event
            GameEventTracker.getTracker().endGameEvent(DEADBOI);
        }
    },
    FIRE(
            "Fetch the Engines",
            "One of your buildings is burning down",
            Color.RED) {
        @Override
        public boolean isValid() {
            // Only one fire can happen at a time, for pacing
            return !GameEventTracker.getTracker().getActiveGameEvents().contains(FIRE);
        }
    },
    FIRE_INSURANCE(
            "Fire Insurance",
            "BurniCare insurance has accepted your claim",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return GameEventTracker.getTracker().getActiveGameEvents().contains(FIRE);
        }
    },
    SILVERFISH(
            "A Silver Menace",
            "Silverfish are infesting an accommodation",
            Color.RED) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    GOVERNMENT_GRANT(
            "Beneficiary",
            "The government has given you a grant",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public void startEffect() {
            GameState.getState().money += 10000f;
            GameEventTracker.getTracker().endGameEvent(GOVERNMENT_GRANT);
        }
    },
    COMMUNITY(
            "The People's Choice",
            "You have received community recognition",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    LIBRARY_DONO(
            "Read All About It",
            "The libraries have received a book donation",
            Color.GREEN) {
        private final float BONUS = 20000;
        private final float MAXBONUS = 100000;

        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public void startEffect() {
            // A bonus amount of money is given per library up to a maximum
            float grant = Math.min(MAXBONUS, BONUS * GameState.getState().getBuildingCount(BuildingType.LIBRARY));
            GameState.getState().money += grant;
            // TODO make a money particle spawn on all libraries
            GameEventTracker.getTracker().endGameEvent(LIBRARY_DONO);
        }
    },
    FOOD_HYGEINE(
            "Poor Hygeine Rating",
            "A food shop has failed a hygeine inspection",
            Color.RED) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    TRENDING(
            "World-Wide-Wonder",
            "A student's \"Day in the Life\" post has gone viral",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    WATER_FAIL(
            "Poisoned the Well",
            "An accomodations water supply is contaminated",
            Color.RED) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    FLAT_PARTY(
            "The Uni Life",
            "Some students are having a wild flat party",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    NOISE_COMPLAINT(
            "Keep it Down!",
            "The flat party has received noise complaints",
            Color.RED) {
        @Override
        public boolean isValid() {
            return GameEventTracker.getTracker().getActiveGameEvents().contains(FLAT_PARTY);
        }
    },
    VARSITY(
            "Allez les Canards",
            "Inter-university varsity, go team!",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    GOOD_PROTEST(
            "We Can Do Better",
            "Some students have organised a protest on campus",
            Color.GREEN) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    BAD_PROTEST(
            "We Can Do Better",
            "Some students have organised a protest on campus",
            Color.RED) {
        @Override
        public boolean isValid() {
            return true;
        }
    },
    SCANDAL(
            "For Shame",
            "A university member has been caught breaking the law",
            Color.RED) {
        @Override
        public boolean isValid() {
            return true;
        }
    };

    private final String displayName;
    private final String eventMessage;
    private final Color titleColor;

    public abstract boolean isValid();

    public void startEffect() {
    };

    public void ongoingEffect() {
    };

    public void endEffect() {
    };

    /**
     * Creates a {@link GameEventType} with specified attributes
     *
     * @param displayName
     * @param scoreEffect
     */
    GameEventType(String displayName, String eventMessage, Color color) {
        this.displayName = displayName;
        this.eventMessage = eventMessage;
        this.titleColor = color;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public Color getColor() {
        return titleColor;
    }
}
