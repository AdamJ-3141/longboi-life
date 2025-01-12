package com.spacecomplexity.longboilife.game.gameevent;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.globals.Constants;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainTimer;
import com.spacecomplexity.longboilife.game.utils.EventHandler;
import com.spacecomplexity.longboilife.game.utils.SatisfactionModifier;

/**
 * Represents the different Game Events that can occur during gameplay.
 */
public enum GameEventType {
    GOVERNMENT_GRANT(
            "Beneficiary",
            "The government has given you a grant",
            Color.GREEN,
            false,
            2 * 60 * 1000) {
        private final float GRANT = 50000;

        @Override
        public boolean isValid() {
            return hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            super.startEffect();
            GameState.getState().money += GRANT;
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    CELEBRITY(
            "Celebrity Visit",
            "A celebrity is visiting the university",
            Color.GREEN,
            false,
            2 * 60 * 1000,
            20 * 1000) {
        private final float MODIFIER = 0.05f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.absolute += MODIFIER;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.absolute -= MODIFIER;
        }
    },
    COMMUNITY(
            "The People's Choice",
            "You have received community recognition",
            Color.GREEN,
            false,
            2 * 60 * 1000,
            40 * 1000) {
        private final float MODIFIER = 0.05f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.absolute += MODIFIER;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.absolute -= MODIFIER;
        }
    },
    TRENDING(
            "World-Wide-Wonder",
            "A student's post has gone viral",
            Color.GREEN,
            false,
            1 * 60 * 1000,
            20 * 1000) {
        private final float MULTIPLIER = 0.01f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.relative += MULTIPLIER;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.relative -= MULTIPLIER;
        }
    },
    PROTEST_GOOD(
            "We Can Do Better",
            "Students have organised a protest on campus",
            Color.GREEN,
            false,
            1 * 60 * 1000) {
        private final float MULTIPLIER = 0.01f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.relative += MULTIPLIER;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.relative -= MULTIPLIER;
        }
    },
    PROTEST_BAD(
            "We Can Do Better",
            "Students have organised a protest on campus",
            Color.RED,
            false,
            1 * 60 * 1000) {
        private final float MULTIPLIER = -0.01f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.relative += MULTIPLIER;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.relative -= MULTIPLIER;
        }
    },
    DISEASE(
            "Fresher's Flu",
            "Fresher's flu runs rampant",
            Color.RED,
            false,
            2 * 60 * 1000,
            10 * 1000) {
        private final float MODIFIER = -0.02f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.absolute += MODIFIER;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.absolute -= MODIFIER;
        }
    },
    SCANDAL(
            "For Shame",
            "A professor has been caught breaking the law",
            Color.RED,
            false,
            3 * 60 * 1000,
            30 * 1000) {
        private final float MODIFIER = -0.01f;
        private final float FINE = 1000f;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.absolute += MODIFIER;
            GameState.getState().money -= FINE;
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.absolute -= MODIFIER;
        }
    },
    LIBRARY_DONO(
            "Read All About It",
            "The libraries have received a book donation",
            Color.GREEN,
            false,
            2 * 60 * 1000) {
        private final float BONUS = 20000;
        private final float MAXBONUS = 100000;

        @Override
        public boolean isValid() {
            return hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            // A bonus amount of money is given per library up to a maximum
            float grant = Math.min(MAXBONUS, BONUS * GameState.getState().getBuildingCount(BuildingType.LIBRARY));
            GameState.getState().money += grant;

            float cellSize = Constants.TILE_SIZE * GameState.getState().scaleFactor;
            for (Building building : GameState.getState().gameWorld.buildings) {
                if (building.getType() == BuildingType.LIBRARY) {
                    EventHandler.getEventHandler().callEvent(EventHandler.Event.SPAWN_PARTICLE,
                            Gdx.files.internal("particles/effects/money.p"), Gdx.files.internal("particles/images"),
                            (building.getPosition().x + building.getType().getSize().x / 2) * cellSize,
                            (building.getPosition().y + building.getType().getSize().x / 2) * cellSize);
                }
            }
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    VARSITY(
            "Allez les Canards",
            "Inter-university varsity, go team!",
            Color.GREEN,
            false,
            1 * 60 * 1000) {

        private final float MULTIPLIER = 0.05f;
        private final float MAXMULTIPLIER = 0.20f;
        private float multiplier;
        private long startTime;

        @Override
        public boolean isValid() {
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            // A bonus amount of satisfaction is given per sports building up to a maximum
            multiplier = Math.min(MAXMULTIPLIER,
                    MULTIPLIER * GameState.getState().getBuildingCount(BuildingCategory.RECREATIONAL));
            GameState.getState().globalSatisfactionModifier.relative += multiplier;
            float cellSize = Constants.TILE_SIZE * GameState.getState().scaleFactor;

            for (Building building : GameState.getState().gameWorld.buildings) {
                if (building.getType().getCategory() == BuildingCategory.RECREATIONAL) {
                    EventHandler.getEventHandler().callEvent(EventHandler.Event.SPAWN_PARTICLE,
                            Gdx.files.internal("particles/effects/heart.p"), Gdx.files.internal("particles/images"),
                            (building.getPosition().x + building.getType().getSize().x / 2) * cellSize,
                            (building.getPosition().y + building.getType().getSize().x / 2) * cellSize);
                }
            }
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= duration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.relative -= multiplier;
        }
    },
    LONGBOI(
            "Feathery Friend",
            "A notable duck on campus is gaining fame",
            Color.GREEN,
            false) {
        private final float MODIFIER = 0.03f;

        @Override
        public boolean isValid() {
            return !isActive(this) && !GameEventTracker.getTracker().getPassedGameEvents().containsKey(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.absolute += MODIFIER;
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.absolute -= MODIFIER;
        }
    },
    DEADBOI(
            "Duck's Demise",
            "Disaster! The famous duck has passed away",
            Color.RED,
            false) {
        @Override
        public boolean isValid() {
            return isActive(LONGBOI);
        }

        @Override
        public void startEffect() {
            GameEventTracker.getTracker().endGameEvent(LONGBOI);
            // Self-ending event
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    FLAT_PARTY(
            "The Uni Life",
            "A wild flat party has started",
            Color.GREEN,
            false,
            1 * 60 * 1000,
            40 * 1000) {
        private final float MULTIPLIER = 0.3f;
        private final long MINDURATION = 10 * 1000;
        private long startTime;
        private long partyDuration;

        @Override
        public boolean isValid() {
            if (GameState.getState().getBuildingCount(BuildingCategory.ACCOMMODATION) <= 0) {
                return false;
            }
            return !isActive(this) && hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            GameState.getState().globalSatisfactionModifier.relative += MULTIPLIER;
            partyDuration = random.nextLong(MINDURATION, duration);
            startTime = MainTimer.getTimerManager().getTimer().getTimeLeft();
        }

        @Override
        public void ongoingEffect() {
            if (startTime - MainTimer.getTimerManager().getTimer().getTimeLeft() >= partyDuration) {
                GameEventTracker.getTracker().endGameEvent(this);
            }
        }

        @Override
        public void endEffect() {
            GameState.getState().globalSatisfactionModifier.relative -= MULTIPLIER;
        }
    },
    NOISE_COMPLAINT(
            "Keep it Down!",
            "The flat party has received noise complaints",
            Color.RED,
            true) {
        @Override
        public boolean isValid() {
            return isActive(FLAT_PARTY);
        }

        @Override
        public void startEffect() {
            GameEventTracker.getTracker().endGameEvent(FLAT_PARTY);
            // Self-ending event
            GameEventTracker.getTracker().endGameEvent(NOISE_COMPLAINT);
        }
    },
    FIRE(
            "Fetch the Engines",
            "One of your buildings is burning down",
            Color.RED,
            false,
            2 * 60 * 1000) {
        private final int FULL_ACCEPTANCE_RATE = 50;
        private final int PART_ACCEPTANCE_RATE = 30;

        @Override
        public boolean isValid() {
            // Only one fire can happen at a time, for pacing
            if (isActive(this)) {
                return false;
            }
            if (GameState.getState().getBuildingCount() <= 0) {
                return false;
            }
            return hasPassedCooldown(this);
        }

        @Override
        public void startEffect() {
            // We don't want to burn down a road, as that would just be boring
            Building[] buildings = GameState.getState().gameWorld.getBuildings().stream()
                    .filter(b -> b.getType().getCategory() != BuildingCategory.PATHWAY).toArray(Building[]::new);
            Building buildingToDestroy = buildings[random.nextInt(buildings.length)];

            // The cost of the building is stored so that they can receive money from
            // insurance
            GameEventTracker.getTracker().trackData(GameEventTrackable.FIRE_BUILDING_COST,
                    buildingToDestroy.getType().getCost());

            // We will choose the type of fire insurance to have here because it lets us
            // keep the weighting code in one place
            int acceptance_die = random.nextInt(100);
            if (acceptance_die <= FULL_ACCEPTANCE_RATE) {
                GameEventTracker.getTracker().trackData(GameEventTrackable.NEXT_FIRE_INSURANCE, FIRE_INSURANCE_GOOD);
            } else if (acceptance_die <= FULL_ACCEPTANCE_RATE + PART_ACCEPTANCE_RATE) {
                GameEventTracker.getTracker().trackData(GameEventTrackable.NEXT_FIRE_INSURANCE, FIRE_INSURANCE_MEDIUM);
            } else {
                GameEventTracker.getTracker().trackData(GameEventTrackable.NEXT_FIRE_INSURANCE, FIRE_INSURANCE_BAD);
            }

            float cellSize = Constants.TILE_SIZE * GameState.getState().scaleFactor;
            EventHandler.getEventHandler().callEvent(EventHandler.Event.SPAWN_PARTICLE,
                    Gdx.files.internal("particles/effects/fire.p"), Gdx.files.internal("particles/images"),
                    (buildingToDestroy.getPosition().x + buildingToDestroy.getType().getSize().x / 2) * cellSize,
                    (buildingToDestroy.getPosition().y + buildingToDestroy.getType().getSize().x / 2) * cellSize);
            GameState.getState().gameWorld.demolish(buildingToDestroy);

        }
    },
    FIRE_INSURANCE_GOOD(
            "Fire Insurance Approved",
            "BurniCare insurance has accepted your claim",
            Color.GREEN,
            true) {
        @Override
        public boolean isValid() {
            return isActive(FIRE)
                    && GameEventTracker.getTracker().retreiveData(GameEventTrackable.NEXT_FIRE_INSURANCE) == this;
        }

        @Override
        public void startEffect() {
            GameState.getState().money += (float) GameEventTracker.getTracker()
                    .retreiveData(GameEventTrackable.FIRE_BUILDING_COST);
            GameEventTracker.getTracker().endGameEvent(FIRE);
            // Self-ending event
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    FIRE_INSURANCE_MEDIUM(
            "Fire Insurance Approved",
            "BurniCare insurance has accepted your claim",
            Color.YELLOW,
            true) {
        @Override
        public boolean isValid() {
            return isActive(FIRE)
                    && GameEventTracker.getTracker().retreiveData(GameEventTrackable.NEXT_FIRE_INSURANCE) == this;
        }

        @Override
        public void startEffect() {
            float maxMoney = (float) GameEventTracker.getTracker().retreiveData(GameEventTrackable.FIRE_BUILDING_COST);
            GameState.getState().money += Math.floor(random.nextFloat(maxMoney / 2, maxMoney));
            GameEventTracker.getTracker().endGameEvent(FIRE);
            // Self-ending event
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    FIRE_INSURANCE_BAD(
            "Fire Insurance Denied",
            "BurniCare insurance has rejected your claim",
            Color.RED,
            true) {
        @Override
        public boolean isValid() {
            return isActive(FIRE)
                    && GameEventTracker.getTracker().retreiveData(GameEventTrackable.NEXT_FIRE_INSURANCE) == this;
        }

        @Override
        public void startEffect() {
            GameEventTracker.getTracker().endGameEvent(FIRE);
            // Self-ending event
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    SILVERFISH(
            "A Silver Menace",
            "Silverfish are infesting an accommodation",
            Color.RED,
            false,
            1 * 60 * 1000) {
        private final float DEBUFF = 0.2f;

        @Override
        public boolean isValid() {
            return hasPassedCooldown(this) && GameState.getState().getBuildingCount(BuildingCategory.ACCOMMODATION) > 0;
        }

        @Override
        public void startEffect() {
            // Infect an accomodation
            Building[] buildings = GameState.getState().gameWorld.getBuildings().stream()
                    .filter(b -> b.getType().getCategory() == BuildingCategory.ACCOMMODATION).toArray(Building[]::new);
            Building buildingToInfect = buildings[random.nextInt(buildings.length)];
            GameState.getState().accomSatisfactionModifiers.put(buildingToInfect,
                    new SatisfactionModifier(-DEBUFF, false));
            GameEventTracker.getTracker().endGameEvent(this);
        }
    },
    WATER_FAIL(
            "Poisoned the Well",
            "A building's water supply is contaminated",
            Color.RED,
            false,
            2 * 60 * 1000) {
        private final float DEBUFF = 0.3f;

        @Override
        public boolean isValid() {
            return hasPassedCooldown(this) && GameState.getState().getBuildingCount(BuildingCategory.ACCOMMODATION) > 0;
        }

        @Override
        public void startEffect() {
            // Infect an accomodation
            Building[] buildings = GameState.getState().gameWorld.getBuildings().stream()
                    .filter(b -> b.getType().getCategory() == BuildingCategory.ACCOMMODATION).toArray(Building[]::new);
            Building buildingToInfect = buildings[random.nextInt(buildings.length)];
            GameState.getState().accomSatisfactionModifiers.put(buildingToInfect,
                    new SatisfactionModifier(-DEBUFF, false));
            GameEventTracker.getTracker().endGameEvent(this);
        }
    };

    private static final Random random = new Random();
    private final String displayName;
    private final String eventMessage;
    private final Color titleColor;
    public final boolean urgent;
    protected final long cooldown; // The minimum time between this event being valid
    protected final long duration;

    /**
     * @return {@code true} if this event can occur
     */
    public abstract boolean isValid();

    /**
     * The effect that an event will have when it starts
     */
    public void startEffect() {
    };

    /**
     * The effect that an event will have as it progresses
     */
    public void ongoingEffect() {
    };

    /**
     * The effect that an event will have when it ends. Cleanup should occur here
     */
    public void endEffect() {
    };

    GameEventType(String displayName, String eventMessage, Color color, boolean urgent) {
        this.displayName = displayName;
        this.eventMessage = eventMessage;
        this.titleColor = color;
        this.urgent = urgent;
        this.cooldown = 0;
        this.duration = 0;
    }

    GameEventType(String displayName, String eventMessage, Color color, boolean urgent, long cooldown) {
        this.displayName = displayName;
        this.eventMessage = eventMessage;
        this.titleColor = color;
        this.urgent = urgent;
        this.cooldown = cooldown;
        this.duration = 0;
    }

    GameEventType(String displayName, String eventMessage, Color color, boolean urgent, long cooldown, long duration) {
        this.displayName = displayName;
        this.eventMessage = eventMessage;
        this.titleColor = color;
        this.urgent = urgent;
        this.cooldown = cooldown;
        this.duration = duration;
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

    /**
     * An urgent event will occur next as soon as it is valid
     *
     * @return true if an event is urgent
     */
    public boolean isUrgent() {
        return urgent;
    }

    /**
     *
     * @param type
     * @return the time since the last event of {@code type} occured.
     *         {@link Long#MAX_VALUE} by default
     */
    protected long timeSinceLastEvent(GameEventType type) {
        long lastEventTime = (GameEventTracker.getTracker().getPassedGameEvents().get(type) != null)
                ? GameEventTracker.getTracker().getPassedGameEvents().get(type)
                : Long.MAX_VALUE;
        long timeSinceLastEvent = lastEventTime - MainTimer.getTimerManager().getTimer().getTimeLeft();
        return timeSinceLastEvent;
    }

    /**
     *
     * @param type
     * @return {@code true} if this game event is currently ongoing
     */
    protected boolean isActive(GameEventType type) {
        return GameEventTracker.getTracker().getActiveGameEvents().contains(type);
    }

    /**
     *
     * @param type
     * @return {@code true} if the game event last happened long enough ago to have
     *         passed its cooldown
     */
    protected boolean hasPassedCooldown(GameEventType type) {
        return timeSinceLastEvent(type) >= type.cooldown;
    }

}
