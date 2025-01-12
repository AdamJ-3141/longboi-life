package com.spacecomplexity.longboilife.headless;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.gameevent.GameEventTrackable;
import com.spacecomplexity.longboilife.game.gameevent.GameEventTracker;
import com.spacecomplexity.longboilife.game.gameevent.GameEventType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;

public class GameEventTypeValidityTests extends AbstractHeadlessGdxTest {
    public GameEventTracker tracker;

    @BeforeEach
    public void getTracker() {
        tracker = GameEventTracker.getTracker();
        tracker.reset();
    }

    @Test
    public void GOVERNMENT_GRANTisValidTest() {
        GameEventType typeUnderTest = GameEventType.GOVERNMENT_GRANT;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "GOVERNMENT_GRANT should be valid by default and was not");
        // Make the event invalid, self-ending cooldown event
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void CELEBRITYIsValidTest() {
        GameEventType typeUnderTest = GameEventType.CELEBRITY;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "CELEBRITY should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void COMMUNITYisValidTest() {
        GameEventType typeUnderTest = GameEventType.COMMUNITY;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "COMMUNITY should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void TRENDINGisValidTest() {
        GameEventType typeUnderTest = GameEventType.TRENDING;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "TRENDING should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void GOOD_PROTESTisValidTest() {
        GameEventType typeUnderTest = GameEventType.PROTEST_GOOD;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "GOOD_PROTEST should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void BAD_PROTESTisValidTest() {
        GameEventType typeUnderTest = GameEventType.PROTEST_BAD;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "BAD_PROTEST should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void DISEASEIsValidTest() {
        GameEventType typeUnderTest = GameEventType.DISEASE;

        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "DISEASE should be valid by default and was not");
        // Make the event invalid, invalid when in cooldown
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void SCANDALisValidTest() {
        GameEventType typeUnderTest = GameEventType.SCANDAL;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "SCANDAL should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void LIBRARY_DONOisValidTest() {
        GameEventType typeUnderTest = GameEventType.LIBRARY_DONO;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "LIBRARY_DONO should be valid by default and was not");
        // Make the event invalid, self-ending cooldown event
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void VARSITYisValidTest() {
        GameEventType typeUnderTest = GameEventType.VARSITY;
        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "VARSITY should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void LONGBOIIsValidTest() {
        GameEventType typeUnderTest = GameEventType.LONGBOI;

        // Make the event valid
        // Event is valid by default
        assertTrue(typeUnderTest.isValid(), "LONGBOI should be valid by default and was not");
        // Make the event invalid
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void DEADBOIIsValidTest() {
        GameEventType typeUnderTest = GameEventType.DEADBOI;
        // Make the event valid
        // Event is invalid by default and requires LONGBOI to be active
        assertFalse(typeUnderTest.isValid(), "DEADBOI should not be valid by default and was");
        tracker.startGameEvent(GameEventType.LONGBOI);
        assertTrue(typeUnderTest.isValid(), "DEADBOI should be valid when LONGBOI is active");
        // Make the event invalid
        tracker.endGameEvent(GameEventType.LONGBOI);
        assertFalse(typeUnderTest.isValid(), "DEADBOI should not be valid when LONGBOI is not active");
    }

    @Test
    public void FLAT_PARTYisValidTest() {
        GameEventType typeUnderTest = GameEventType.FLAT_PARTY;
        Building testAccom = new Building(BuildingType.LUXURYFLATS, new Vector2Int(0, 0));
        // Make the event valid
        // Event is invalid by default and requires a building of category ACCOMODATION
        // to exist
        assertFalse(typeUnderTest.isValid(), "FLAT_PARTY should not be valid by default and was");
        GameState.getState().gameWorld.build(testAccom);
        assertTrue(typeUnderTest.isValid(),
                "FLAT_PARTY should be valid when an accom exists and it is not on cooldown");

        // Make the event invalid by starting it, invalid by cooldown
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void NOISE_COMPLAINTisValidTest() {
        GameEventType typeUnderTest = GameEventType.NOISE_COMPLAINT;
        // Inititally invalid
        assertFalse(typeUnderTest.isValid(), "NOISE_COMPLIANT should not be valid by default and was");
        // Valid when a fire is active and when the fire has selected this type of
        // insurance, stored in the tracker
        tracker.startGameEvent(GameEventType.FLAT_PARTY);
        assertTrue(typeUnderTest.isValid(),
                "NOISE_COMPLAINT should be valid when a party is active");
    }

    @Test
    public void FIREisValidTest() {
        GameEventType typeUnderTest = GameEventType.FIRE;
        Building testBuilding = new Building(BuildingType.BIKESTORE, new Vector2Int(0, 0));
        // Make the event valid
        // Event is invalid by default and requires a building to exist
        assertFalse(typeUnderTest.isValid(), "FIRE should not be valid by default and was");
        GameState.getState().gameWorld.build(testBuilding);
        assertTrue(typeUnderTest.isValid(), "FIRE should be valid when a building exists and it is not on cooldown");

        // Make the event invalid by ending it, which should kill the building
        tracker.startGameEvent(typeUnderTest);
        // This line is important because otherwise the event will be invalid because
        // there will be no buildings
        GameState.getState().gameWorld.build(testBuilding);
        // Note that FIRE is a self-ending event
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void FIRE_INSURANCE_GOODisValidTest() {
        GameEventType typeUnderTest = GameEventType.FIRE_INSURANCE_GOOD;
        Building testBuilding = new Building(BuildingType.BIKESTORE, new Vector2Int(0, 0));
        // Inititally invalid
        assertFalse(typeUnderTest.isValid(), "FIRE_INSURANCE should not be valid by default and was");
        // Valid when a fire is active and when the fire has selected this type of
        // insurance, stored in the tracker
        GameState.getState().gameWorld.build(testBuilding);
        tracker.startGameEvent(GameEventType.FIRE);
        tracker.trackData(GameEventTrackable.NEXT_FIRE_INSURANCE, typeUnderTest);
        assertTrue(typeUnderTest.isValid(),
                "FIRE_INSURANCE should be valid when FIRE is active and it is the chosen next fire insurance type and was not");
    }

    @Test
    public void FIRE_INSURANCE_BADisValidTest() {
        GameEventType typeUnderTest = GameEventType.FIRE_INSURANCE_BAD;
        Building testBuilding = new Building(BuildingType.BIKESTORE, new Vector2Int(0, 0));
        // Inititally invalid
        assertFalse(typeUnderTest.isValid(), "FIRE_INSURANCE should not be valid by default and was");
        // Valid when a fire is active and when the fire has selected this type of
        // insurance, stored in the tracker
        GameState.getState().gameWorld.build(testBuilding);
        tracker.startGameEvent(GameEventType.FIRE);
        tracker.trackData(GameEventTrackable.NEXT_FIRE_INSURANCE, typeUnderTest);
        assertTrue(typeUnderTest.isValid(),
                "FIRE_INSURANCE should be valid when FIRE is active and it is the chosen next fire insurance type and was not");
    }

    @Test
    public void FIRE_INSURANCE_MEDIUMisValidTest() {
        GameEventType typeUnderTest = GameEventType.FIRE_INSURANCE_MEDIUM;
        Building testBuilding = new Building(BuildingType.BIKESTORE, new Vector2Int(0, 0));
        // Inititally invalid
        assertFalse(typeUnderTest.isValid(), "FIRE_INSURANCE should not be valid by default and was");
        // Valid when a fire is active and when the fire has selected this type of
        // insurance, stored in the tracker
        GameState.getState().gameWorld.build(testBuilding);
        tracker.startGameEvent(GameEventType.FIRE);
        tracker.trackData(GameEventTrackable.NEXT_FIRE_INSURANCE, typeUnderTest);
        assertTrue(typeUnderTest.isValid(),
                "FIRE_INSURANCE should be valid when FIRE is active and it is the chosen next fire insurance type and was not");
    }

    @Test
    public void SILVERFISHisValidTest() {
        GameEventType typeUnderTest = GameEventType.SILVERFISH;
        Building testAccom = new Building(BuildingType.LUXURYFLATS, new Vector2Int(0, 0));
        // Make the event valid
        // Event is invalid by default and requires a building of category ACCOMODATION
        // to exist
        assertFalse(typeUnderTest.isValid(), "SILVERFISH should not be valid by default and was");
        GameState.getState().gameWorld.build(testAccom);
        assertTrue(typeUnderTest.isValid(),
                "SILVERFISH should be valid when an accom exists and it is not on cooldown");

        // Make the event invalid by starting it, invalid by cooldown
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }

    @Test
    public void WATER_FAILisValidTest() {
        GameEventType typeUnderTest = GameEventType.WATER_FAIL;
        Building testAccom = new Building(BuildingType.LUXURYFLATS, new Vector2Int(0, 0));
        // Make the event valid
        // Event is invalid by default and requires a building of category ACCOMODATION
        // to exist
        assertFalse(typeUnderTest.isValid(), "WATER_FAIL should not be valid by default and was");
        GameState.getState().gameWorld.build(testAccom);
        assertTrue(typeUnderTest.isValid(),
                "WATER_FAIL should be valid when an accom exists and it is not on cooldown");

        // Make the event invalid by starting it, invalid by cooldown
        tracker.startGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when active and was not");
        tracker.endGameEvent(typeUnderTest);
        assertFalse(typeUnderTest.isValid(), "GameEvent should be invalid when on cooldown and was not");
    }
}
