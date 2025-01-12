package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.building.BuildingCategory;
import com.spacecomplexity.longboilife.game.gameevent.GameEventTrackable;
import com.spacecomplexity.longboilife.game.gameevent.GameEventTracker;
import com.spacecomplexity.longboilife.game.gameevent.GameEventType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;

public class GameEventTypeContentTests extends AbstractHeadlessGdxTest {
    public GameEventTracker tracker;
    public GameState state;
    public float defaultMoney;
    public float defaultAbsoluteSatisfactionMod;
    public float defaultRelativeSatisfactionMod;

    @BeforeEach
    public void getTrackerAndState() {
        state = GameState.getState();
        tracker = GameEventTracker.getTracker();
        // state.reset();
        // Resetting the state should reset the tracker anyway
        tracker.reset();

        defaultMoney = state.money;
        defaultAbsoluteSatisfactionMod = state.globalSatisfactionModifier.absolute;
        defaultRelativeSatisfactionMod = state.globalSatisfactionModifier.relative;

        // As a precondition we expect the satisfaction modifiers to be:
        // Absolute: 0, Relative: 1
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#money} by some value
     */
    @Test
    public void GOVERNMENT_GRANTeffectTest() {
        GameEventType typeUnderTest = GameEventType.GOVERNMENT_GRANT;
        final float GRANT = 50000;

        assertEquals(defaultMoney, state.money,
                "Precondition failed, expected default money");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultMoney + GRANT, state.money,
                "GameEvent did not increase money upon starting");

        // Event should also end itself
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent did not end itself");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the ABSOLUTE satisfaction
     */
    @Test
    public void CELEBRITYeffectTest() {
        GameEventType typeUnderTest = GameEventType.CELEBRITY;
        final float CELEBRITY_MODIFIER = 0.05f;

        assertEquals(defaultAbsoluteSatisfactionMod, state.globalSatisfactionModifier.absolute,
                "Precondition failed, expected default absolute global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod + CELEBRITY_MODIFIER, state.globalSatisfactionModifier.absolute,
                "GameEvent did not increase the global absolute satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod, state.globalSatisfactionModifier.absolute,
                "GameEvent did not decrease the global absolute satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the ABSOLUTE satisfaction
     */
    @Test
    public void COMMUNITYeffectTest() {
        GameEventType typeUnderTest = GameEventType.COMMUNITY;
        final float COMMUNITY_MODIFIER = 0.05f;

        assertEquals(defaultAbsoluteSatisfactionMod, state.globalSatisfactionModifier.absolute,
                "Precondition failed, expected default absolute global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod + COMMUNITY_MODIFIER, state.globalSatisfactionModifier.absolute,
                "GameEvent did not increase the global absolute satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod, state.globalSatisfactionModifier.absolute,
                "GameEvent did not decrease the global absolute satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the RELATIVE satisfaction
     */
    @Test
    public void TRENDINGeffectTest() {
        GameEventType typeUnderTest = GameEventType.TRENDING;
        final float TRENDING_MULTIPLIER = 0.01f;

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + TRENDING_MULTIPLIER, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase the global relative satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "GameEvent did not decrease the global relative satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the RELATIVE satisfaction
     */
    @Test
    public void PROTEST_GOODeffectTest() {
        GameEventType typeUnderTest = GameEventType.PROTEST_GOOD;
        final float PROTEST_GOOD_MULTIPLIER = 0.01f;

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + PROTEST_GOOD_MULTIPLIER,
                state.globalSatisfactionModifier.relative,
                "GameEvent did not increase the global relative satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "GameEvent did not decrease the global relative satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to decrease {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the RELATIVE satisfaction
     */
    @Test
    public void PROTEST_BADeffectTest() {
        GameEventType typeUnderTest = GameEventType.PROTEST_BAD;
        final float PROTEST_BAD_MULTIPLIER = -0.01f;

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + PROTEST_BAD_MULTIPLIER, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase the global relative satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "GameEvent did not decrease the global relative satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to decrease {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the ABSOLUTE satisfaction
     */
    @Test
    public void DISEASEeffectTest() {
        GameEventType typeUnderTest = GameEventType.DISEASE;
        final float DISEASE_MODIFIER = -0.02f;

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default absolute global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod + DISEASE_MODIFIER, state.globalSatisfactionModifier.absolute,
                "GameEvent did not decrease the global absolute satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "GameEvent did not decrease the global absolute satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to decrease {@link GameState#globalSatisfactionModifier}
     * by some value, and reduce {@link GameState#money}.
     * Currently this event affects the ABSOLUTE satisfaction
     */
    @Test
    public void SCANDALeffectTest() {
        GameEventType typeUnderTest = GameEventType.SCANDAL;
        final float SCANDAL_MODIFIER = -0.01f;
        final float SCANDAL_FINE = 1000f;

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default absolute global satisfaction");
        assertEquals(defaultMoney, state.money,
                "Precondition failed, expected default money");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod + SCANDAL_MODIFIER, state.globalSatisfactionModifier.absolute,
                "GameEvent did not decrease the global absolute satisfaction modifier upon starting");
        assertEquals(defaultMoney - SCANDAL_FINE, state.money,
                "GameEvent did not decrease money upon starting");

        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase the global absolute satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#money}, by some value for
     * every {@link BuildingType#LIBRARY}.
     */
    @Test
    public void LIBRARY_DONO1BuildingEffectTest() {
        GameEventType typeUnderTest = GameEventType.LIBRARY_DONO;
        final float LIBRARY_BONUS = 20000;

        Building testBuilding = new Building(BuildingType.LIBRARY, new Vector2Int(0,
                0));

        assertEquals(defaultMoney, state.money, "Precondition failed, expected default money");

        state.gameWorld.build(testBuilding);
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultMoney + LIBRARY_BONUS, state.money, "GameEvent did not increase money upon starting");

        // Event should also end itself
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent did not end itself");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#money}, by some value for
     * every {@link BuildingType#LIBRARY}.
     */
    @Test
    public void LIBRARY_DONO3BuildingEffectTest() {
        GameEventType typeUnderTest = GameEventType.LIBRARY_DONO;
        final float LIBRARY_BONUS = 20000;
        Building testBuilding1 = new Building(BuildingType.LIBRARY, new Vector2Int(0,
                0));
        Building testBuilding2 = new Building(BuildingType.LIBRARY, new Vector2Int(4,
                0));
        Building testBuilding3 = new Building(BuildingType.LIBRARY, new Vector2Int(8,
                0));

        assertEquals(defaultMoney, state.money, "Precondition failed, expected default money");

        state.gameWorld.build(testBuilding1);
        state.gameWorld.build(testBuilding2);
        state.gameWorld.build(testBuilding3);
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultMoney + 3 * LIBRARY_BONUS, state.money, "GameEvent did not increase money upon starting");

        // Event should also end itself
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent did not end itself");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#money}, by some value for
     * every {@link BuildingType#LIBRARY}. There is a max value that this can
     * increase by
     */
    @Test
    public void LIBRARY_DONO6BuildingEffectTest() {
        GameEventType typeUnderTest = GameEventType.LIBRARY_DONO;
        final float LIBRARY_MAXBONUS = 100000;
        Building testBuilding1 = new Building(BuildingType.LIBRARY, new Vector2Int(0,
                0));
        Building testBuilding2 = new Building(BuildingType.LIBRARY, new Vector2Int(4,
                0));
        Building testBuilding3 = new Building(BuildingType.LIBRARY, new Vector2Int(8,
                0));
        Building testBuilding4 = new Building(BuildingType.LIBRARY, new Vector2Int(12,
                0));
        Building testBuilding5 = new Building(BuildingType.LIBRARY, new Vector2Int(16,
                0));
        Building testBuilding6 = new Building(BuildingType.LIBRARY, new Vector2Int(20,
                0));

        assertEquals(defaultMoney, state.money, "Precondition failed, expected default money");

        state.gameWorld.build(testBuilding1);
        state.gameWorld.build(testBuilding2);
        state.gameWorld.build(testBuilding3);
        state.gameWorld.build(testBuilding4);
        state.gameWorld.build(testBuilding5);
        state.gameWorld.build(testBuilding6);
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultMoney + LIBRARY_MAXBONUS, state.money, "GameEvent did not increase money upon starting");

        // Event should also end itself
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent did not end itself");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase
     * {@link GameState#globalSatisfactionModifier}, by some value for every
     * {@link BuildingCategory#RECREATIONAL}.
     * This event currently affects RELATIVE satisfaction
     */
    @Test
    public void VARSITY1BuildingEffectTest() {
        GameEventType typeUnderTest = GameEventType.VARSITY;
        final float VARSITY_MULTIPLIER = 0.05f;

        Building testBuilding = new Building(BuildingType.GYM, new Vector2Int(0,
                0));

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative satisfaction");

        state.gameWorld.build(testBuilding);
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + VARSITY_MULTIPLIER, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase relative satisfaction upon starting");

        tracker.endGameEvent(typeUnderTest);

        // Avoid floating point imprecision errors
        assertTrue(Math.abs(defaultRelativeSatisfactionMod - state.globalSatisfactionModifier.relative) < 0.001f,
                "GameEvent did not decrease relative satisfaction upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase
     * {@link GameState#globalSatisfactionModifier}, by some value for every
     * {@link BuildingCategory#RECREATIONAL}.
     * This event currently affects RELATIVE satisfaction
     */
    @Test
    public void VARSITY3BuildingEffectTest() {
        GameEventType typeUnderTest = GameEventType.VARSITY;
        final float VARSITY_MULTIPLIER = 0.05f;

        Building testBuilding1 = new Building(BuildingType.GYM, new Vector2Int(0,
                0));
        Building testBuilding2 = new Building(BuildingType.GYM, new Vector2Int(4,
                0));
        Building testBuilding3 = new Building(BuildingType.GYM, new Vector2Int(8,
                0));

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative satisfaction");

        state.gameWorld.build(testBuilding1);
        state.gameWorld.build(testBuilding2);
        state.gameWorld.build(testBuilding3);
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + 3 * VARSITY_MULTIPLIER, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase relative satisfaction upon starting");

        tracker.endGameEvent(typeUnderTest);
        // Avoid floating point imprecision errors
        assertTrue(Math.abs(defaultRelativeSatisfactionMod - state.globalSatisfactionModifier.relative) < 0.001f,
                "GameEvent did not decrease relative satisfaction upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase
     * {@link GameState#globalSatisfactionModifier}, by some value for every
     * {@link BuildingCategory#RECREATIONAL}. There is a max value this can increase
     * by.
     * This event currently affects RELATIVE satisfaction
     */
    @Test
    public void VARSITY5BuildingEffectTest() {
        GameEventType typeUnderTest = GameEventType.VARSITY;
        final float MAX_VARSITY_MULTIPLIER = 0.20f;

        Building testBuilding1 = new Building(BuildingType.GYM, new Vector2Int(0,
                0));
        Building testBuilding2 = new Building(BuildingType.GYM, new Vector2Int(4,
                0));
        Building testBuilding3 = new Building(BuildingType.GYM, new Vector2Int(8,
                0));
        Building testBuilding4 = new Building(BuildingType.GYM, new Vector2Int(12,
                0));
        Building testBuilding5 = new Building(BuildingType.GYM, new Vector2Int(16,
                0));

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative satisfaction");

        state.gameWorld.build(testBuilding1);
        state.gameWorld.build(testBuilding2);
        state.gameWorld.build(testBuilding3);
        state.gameWorld.build(testBuilding4);
        state.gameWorld.build(testBuilding5);
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + MAX_VARSITY_MULTIPLIER, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase relative satisfaction upon starting");

        tracker.endGameEvent(typeUnderTest);
        // Avoid floating point imprecision errors
        assertTrue(Math.abs(defaultRelativeSatisfactionMod - state.globalSatisfactionModifier.relative) < 0.001f,
                "GameEvent did not decrease relative satisfaction upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * We expect this event to increase {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the ABSOLUTE satisfaction
     */
    @Test
    public void LONGBOIeffectTest() {
        GameEventType typeUnderTest = GameEventType.LONGBOI;
        final float LONGBOI_MODIFIER = 0.03f;

        assertEquals(defaultAbsoluteSatisfactionMod, state.globalSatisfactionModifier.absolute,
                "Precondition failed, expected default absolute global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod + LONGBOI_MODIFIER, state.globalSatisfactionModifier.absolute,
                "GameEvent did not increase the global absolute satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertEquals(defaultAbsoluteSatisfactionMod, state.globalSatisfactionModifier.absolute,
                "GameEvent did not decrease the global absolute satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event should only have the effect of ending itself and the
     * {@link GameEventType#LONGBOI} event
     */
    @Test
    public void DEADBOIeffectTest() {
        GameEventType typeUnderTest = GameEventType.DEADBOI;

        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest),
                "Precondition failed: GameEvent should not be active");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.LONGBOI),
                "Precondition failed: LONGBOI GameEvent should not be active");

        tracker.startGameEvent(GameEventType.LONGBOI);
        assertTrue(tracker.getActiveGameEvents().contains(GameEventType.LONGBOI),
                "Precondition failed: LONGBOI GameEvent should be active");

        tracker.startGameEvent(typeUnderTest);
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.LONGBOI),
                "GameEvent should have ended LONGBOI GameEvent");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * Note that while FLAT_PARTY needs an accomodation to exist to count as a valid
     * effect, it does not need this building to exist for its functionality
     * We expect this event to increase {@link GameState#globalSatisfactionModifier}
     * by some value.
     * Currently this event affects the RELATIVE satisfaction
     */
    @Test
    public void FLAT_PARTYeffectTest() {
        GameEventType typeUnderTest = GameEventType.FLAT_PARTY;
        final float PARTY_MULTIPLIER = 0.3f;

        assertEquals(defaultRelativeSatisfactionMod, state.globalSatisfactionModifier.relative,
                "Precondition failed, expected default relative global satisfaction");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultRelativeSatisfactionMod + PARTY_MULTIPLIER, state.globalSatisfactionModifier.relative,
                "GameEvent did not increase the global relative satisfaction modifier upon starting");
        tracker.endGameEvent(typeUnderTest);
        assertTrue(Math.abs(defaultRelativeSatisfactionMod - state.globalSatisfactionModifier.relative) < 0.001f,
                "GameEvent did not decrease the global relative satisfaction correctly upon ending");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event should only have the effect of ending itself and the
     * {@link GameEventType#FLAT_PARTY} event
     */
    @Test
    public void NOISE_COMPLAINTeffectTest() {
        GameEventType typeUnderTest = GameEventType.NOISE_COMPLAINT;

        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest),
                "Precondition failed: GameEvent should not be active");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.FLAT_PARTY),
                "Precondition failed: FLAT_PARTY GameEvent should not be active");

        tracker.startGameEvent(GameEventType.FLAT_PARTY);
        assertTrue(tracker.getActiveGameEvents().contains(GameEventType.FLAT_PARTY),
                "Precondition failed: FLAT_PARTY GameEvent should be active");

        tracker.startGameEvent(typeUnderTest);
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.FLAT_PARTY),
                "GameEvent should have ended FLAT_PARTY GameEvent");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event needs a building to exist to work. The event will destroy the
     * building, choose the type of fire insurance that will be received, and also
     * store the value of the building using {@link GameEventTracker#trackData()}
     */
    @Test
    public void FIREeffectTest() {
        GameEventType typeUnderTest = GameEventType.FIRE;

        Building testBuilding = new Building(BuildingType.GYM, new Vector2Int(0,
                0));

        state.gameWorld.build(testBuilding);
        assertTrue(state.gameWorld.buildings.contains(testBuilding), "Precondition fail: building does not exist");
        tracker.startGameEvent(typeUnderTest);
        assertFalse(state.gameWorld.buildings.contains(testBuilding), "Building was not destroyed");
        assertEquals(testBuilding.getType().getCost(), tracker.retreiveData(GameEventTrackable.FIRE_BUILDING_COST),
                "Burned building cost was not tracked");
        assertNotNull(tracker.retreiveData(GameEventTrackable.NEXT_FIRE_INSURANCE),
                "Next fire insurance was not tracked");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event requires a {@link GameEventType#FIRE} to be active, it will then
     * give the user the full price of the building back
     */
    @Test
    public void FIRE_INSURANCE_GOODeffectTest() {
        GameEventType typeUnderTest = GameEventType.FIRE_INSURANCE_GOOD;

        Building testBuilding = new Building(BuildingType.GYM, new Vector2Int(0,
                0));

        state.gameWorld.build(testBuilding);
        tracker.startGameEvent(GameEventType.FIRE);

        assertEquals(defaultMoney, state.money,
                "Precondition failed, expected default money");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultMoney + testBuilding.getType().getCost(), state.money,
                "GameEvent did not increase money upon starting");

        // Event should also end itself and FIRE
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.FIRE),
                "GameEvent should have ended LONGBOI GameEvent");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event requires a {@link GameEventType#FIRE} to be active, it will then
     * give the user some of the price of the building back, ranging from half to
     * the full price
     */
    @Test
    public void FIRE_INSURANCE_MEDIUMeffectTest() {
        GameEventType typeUnderTest = GameEventType.FIRE_INSURANCE_MEDIUM;

        Building testBuilding = new Building(BuildingType.GYM, new Vector2Int(0,
                0));

        state.gameWorld.build(testBuilding);
        tracker.startGameEvent(GameEventType.FIRE);

        assertEquals(defaultMoney, state.money,
                "Precondition failed, expected default money");
        tracker.startGameEvent(typeUnderTest);
        float buildingCost = testBuilding.getType().getCost();
        assertTrue(defaultMoney + buildingCost / 2 <= state.money && state.money <= defaultMoney + buildingCost,
                "GameEvent did not increase money upon starting");

        // Event should also end itself and FIRE
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.FIRE),
                "GameEvent should have ended LONGBOI GameEvent");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event requires a {@link GameEventType#FIRE} to be active, it will then
     * give the user the full price of the building back
     */
    @Test
    public void FIRE_INSURANCE_BADeffectTest() {
        GameEventType typeUnderTest = GameEventType.FIRE_INSURANCE_BAD;

        Building testBuilding = new Building(BuildingType.GYM, new Vector2Int(0,
                0));

        state.gameWorld.build(testBuilding);
        tracker.startGameEvent(GameEventType.FIRE);

        assertEquals(defaultMoney, state.money,
                "Precondition failed, expected default money");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(defaultMoney, state.money,
                "GameEvent should not increase money upon starting");

        // Event should also end itself and FIRE
        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
        assertFalse(tracker.getActiveGameEvents().contains(GameEventType.FIRE),
                "GameEvent should have ended LONGBOI GameEvent");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event needs a {@link BuildingCategory#ACCOMMODATION} to exist to work.
     * The event will give that building a local debuff, which will need to be
     * removed by "fixing" the building
     */
    @Test
    public void SILVERFISHeffectTest() {
        GameEventType typeUnderTest = GameEventType.SILVERFISH;
        final float SILVERFISH_DEBUFF = 0.2f;

        Building testBuilding = new Building(BuildingType.LUXURYFLATS, new Vector2Int(0,
                0));

        state.gameWorld.build(testBuilding);
        assertTrue(state.gameWorld.buildings.contains(testBuilding), "Precondition fail: building does not exist");
        assertNull(state.accomSatisfactionModifiers.get(testBuilding),
                "Precondition fail: Satisfaction Modifier should not exist");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(-SILVERFISH_DEBUFF, state.accomSatisfactionModifiers.get(testBuilding).relative,
                "Did not decrease the local relative satisfaction modifier");

        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
    }

    /**
     * The effects of the event may be changed in the future, which may cause this
     * test to fail.
     * This event needs a {@link BuildingCategory#ACCOMMODATION} to exist to work.
     * The event will give that building a local debuff, which will need to be
     * removed by "fixing" the building
     */
    @Test
    public void WATER_FAILeffectTest() {
        GameEventType typeUnderTest = GameEventType.WATER_FAIL;
        final float WATER_FAIL_DEBUFF = 0.3f;

        Building testBuilding = new Building(BuildingType.LUXURYFLATS, new Vector2Int(0,
                0));

        state.gameWorld.build(testBuilding);
        assertTrue(state.gameWorld.buildings.contains(testBuilding), "Precondition fail: building does not exist");
        assertNull(state.accomSatisfactionModifiers.get(testBuilding),
                "Precondition fail: Satisfaction Modifier should not exist");
        tracker.startGameEvent(typeUnderTest);
        assertEquals(-WATER_FAIL_DEBUFF, state.accomSatisfactionModifiers.get(testBuilding).relative,
                "Did not decrease the local relative satisfaction modifier");

        assertFalse(tracker.getActiveGameEvents().contains(typeUnderTest), "GameEvent should have ended itself");
    }
}
