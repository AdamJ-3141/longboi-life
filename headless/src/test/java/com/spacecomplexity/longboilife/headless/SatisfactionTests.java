package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.game.building.Building;
import com.spacecomplexity.longboilife.game.building.BuildingType;
import com.spacecomplexity.longboilife.game.utils.AccomSatisfactionDetail;
import com.spacecomplexity.longboilife.game.utils.Vector2Int;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.spacecomplexity.longboilife.game.utils.GameUtils;
import com.spacecomplexity.longboilife.game.world.World;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class SatisfactionTests extends AbstractHeadlessGdxTest{

    private World empty_world;
    private float satisfactionSum;
    private HashMap<Building, AccomSatisfactionDetail> accomSatisfactions;

    public static void assertClose(float expected, float actual, float delta, String message) {
        assertTrue(actual < expected + delta && actual > expected - delta, message);
    }

    public static void assertClose(HashSet<Float> expected, HashSet<Float> actual, float delta, String message) {
        assertEquals(expected.size(), actual.size(), message + "(Sets must be the same size.)");

        for (Float actualValue : actual) {
            boolean foundEquivalent = false;
            for (Float expectedValue : expected) {
                if (Math.abs(actualValue - expectedValue) <= delta) {
                    foundEquivalent = true;
                    break;  // Found a match within the delta, no need to check further
                }
            }
            assertTrue(foundEquivalent, message + "(No match found for " + actualValue + ")");
        }
    }

    @BeforeEach
    public void loadWorld() {
        try {
            empty_world = new World(Gdx.files.internal("test_maps/map_allGrass.json"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void emptyMap() {
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        accomSatisfactions = gameState.accomSatisfaction;
        assertEquals(0, accomSatisfactions.size(), "No accommodation -> No accommodation satisfaction");
        assertEquals(0, satisfactionSum, "No accommodation -> no satisfaction.");
        assertEquals(0, gameState.targetSatisfaction, "No accommodation -> no satisfaction.");
    }

    @Test
    public void oneHalls() {
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));

        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        accomSatisfactions = gameState.accomSatisfaction;
        assertEquals(1, accomSatisfactions.size(), "One accommodation -> One entry to HashMap");
        assertEquals(0, satisfactionSum, "Accommodation has no amenities -> No satisfaction");
        assertEquals(0, gameState.targetSatisfaction, "Accommodation has no amenities -> No satisfaction");
    }

    @Test
    public void hallsNearLibrary() {
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(4,0));
        empty_world.build(BuildingType.ROAD, new Vector2Int(3,0));
        // Path Distance = 0
        // DSat = 1
        // QSat = 1
        // AccomQuality = (2 * arctan(0.9) / pi) + 0.5 = 0.966
        // Category Satisfaction = 1 * 1 = 1
        // No Recreation / Food
        // 0.966 * (1 + 0 + 0) / 3 = 0.322
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        assertClose(0.322f, satisfactionSum, 0.001f,"Library should generate 0.322 satisfaction");
        assertClose(0.322f, gameState.targetSatisfaction, 0.001f,"Library should generate 0.322 satisfaction");
    }

    @Test
    public void hallsFarFromLibrary() {
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(15,0));
        for (int i = 3; i < 15 ; i++) {
            empty_world.build(BuildingType.ROAD, new Vector2Int(i, 0));
        }
        /*
         * Path Distance = 12
         * DSat = 0.3114
         * QSat = 1
         * AccomQuality = (2 * arctan(0.9) / pi) + 0.5 = 0.966
         * Category Satisfaction = 0.3114 * 1 = 0.3114
         * No Recreation / Food
         * 0.966 * (0.3114 + 0 + 0) / 3 = 0.1003
         */
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        assertClose(0.1f, satisfactionSum, 0.001f,"Library should generate 0.322 satisfaction");
        assertClose(0.1f, gameState.targetSatisfaction, 0.001f,"Library should generate 0.322 satisfaction");
    }

    @Test
    public void tooFarFromLibrary() {
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(33,0));
        // Education ignore distance = 30
        for (int i = 3; i < 33 ; i++) {
            empty_world.build(BuildingType.ROAD, new Vector2Int(i, 0));
        }
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        assertEquals(30, empty_world.getBuildings().stream().filter(
            b -> b.getType() == BuildingType.ROAD).count(), "Confirm 30 roads built");
        assertEquals(0, gameState.targetSatisfaction, "Building too far from accommodation -> No satisfaction");
    }

    @Test
    public void moreCategories() {
        /*
         *  Map:
         *                    R R
         *              E E E R R
         *  H H H F F F E E E R R
         *  H H H F F F E E E R R
         *  H H H P P P P P P P P
         *
         *  Dist= 1 2 3 4 5 6 7 8
         *
         *  H = Halls
         *      - Quality: 0.9
         *      - Quality Multiplier: 0.966
         *  F = Food (Nandos)
         *      - Distance: 1
         *      - Distance Multiplier: 1
         *      - Quality Multiplier: 1
         *  R = Recreation (Football Field)
         *      - Distance: 7
         *      - Distance Multiplier: 1
         *      - Quality Multiplier: 0.8
         *  E = Education (Lecture Theatre)
         *      - Distance: 4
         *      - Distance Multiplier: 1
         *      - Quality Multiplier: 0.8
         *  P = Pathway
         *
         *  Total Satisfaction = 0.966 * (1 + 0.8 + 0.8) / 3
         *                     = 0.8377
         */
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.NANDOS, new Vector2Int(3,1));
        empty_world.build(BuildingType.LECTURETHEATRE, new Vector2Int(6,1));
        empty_world.build(BuildingType.FOOTBALLFIELD, new Vector2Int(9,1));
        for (int i = 3; i < 11; i++) {
            empty_world.build(BuildingType.ROAD, new Vector2Int(i, 0));
        }
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        assertClose(0.838f, gameState.targetSatisfaction, 0.001f, "Building too far from accommodation -> No satisfaction");
    }

    @Test
    public void satisfactionOverflow() {

        /* Map:
         *           e e e   E E E E
         *           e e e   E E E E
         *         f P P P r E E E E
         * R R R R P A A A P E E E E
         * R R R R P A A A P F F F
         * R R R R P A A A P F F F
         *
         * A: Accommodation (Luxury Flats)
         *      - Quality: 1.8
         *      - Quality Multiplier: (2 * arctan(1.8) / pi) + 0.5 = 1.177
         * R: Recreation (Gym)
         * r: Recreation (Bike Storage)
         * E: Education (Library)
         * e: Education (Classrooms)
         * F: Food (Nandos)
         * f: Food (Kebab Van)
         * All buildings are a distance of 1 from the accommodation.
         * One of each category has maximum quality.
         * There is an extra of each type.
         */
        empty_world.build(BuildingType.LUXURYFLATS, new Vector2Int(5, 0));

        empty_world.build(BuildingType.ROAD, new Vector2Int(4,0));
        empty_world.build(BuildingType.ROAD, new Vector2Int(4,1));
        empty_world.build(BuildingType.ROAD, new Vector2Int(4,2));
        empty_world.build(BuildingType.ROAD, new Vector2Int(8,0));
        empty_world.build(BuildingType.ROAD, new Vector2Int(8,1));
        empty_world.build(BuildingType.ROAD, new Vector2Int(8,2));
        empty_world.build(BuildingType.ROAD, new Vector2Int(5,3));
        empty_world.build(BuildingType.ROAD, new Vector2Int(6,3));
        empty_world.build(BuildingType.ROAD, new Vector2Int(7,3));

        empty_world.build(BuildingType.NANDOS, new Vector2Int(9, 0));
        empty_world.build(BuildingType.KEBABVAN, new Vector2Int(3, 4));
        empty_world.build(BuildingType.GYM, new Vector2Int(0, 0));
        empty_world.build(BuildingType.BIKESTORE, new Vector2Int(8, 3));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(9, 2));
        empty_world.build(BuildingType.CLASSROOMS, new Vector2Int(5, 4));

        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        assertEquals(1, satisfactionSum, "Satisfaction should be capped at 100% at all points");
        assertEquals(1, gameState.targetSatisfaction, "Satisfaction should be capped at 100%");
    }

    @Test
    public void multipleAccommodationsMirrored() {
        /*  Map:
         *          E E E E
         *          E E E E
         *  H H H   E E E E   H H H
         *  H H H   E E E E   H H H
         *  H H H P P P P P P H H H
         * Test: Halls satisfaction should not be affected by direction
         */
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.HALLS, new Vector2Int(9, 0));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(4, 1));
        for (int i = 3; i < 9 ; i++) {
            empty_world.build(BuildingType.ROAD, new Vector2Int(i, 0));
        }

        // Each satisfaction should be 0.322
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        HashSet<Float> accomSatisfactions = new HashSet<>(gameState.accomSatisfaction.values().stream().map(i -> i.totalSatisfaction).toList());
        assertEquals(2, gameState.accomSatisfaction.size(), "Verify 2 accommodations.");
        assertClose(0.644f, satisfactionSum, 0.001f, "Sum should be close to 0.644");
        assertTrue(accomSatisfactions.stream()
            .allMatch(i -> i < 0.322 + 0.001 && i > 0.322 - 0.001),
            "Both accommodation satisfaction should be close to 0.322");
        assertClose(0.322f, gameState.targetSatisfaction, 0.001f,
            "Target Satisfaction should be the average of accommodations.");
    }

    @Test
    public void multipleAccommodationsNotEqual() {
        /*  Map:
         *          E E E E
         *          E E E E
         *  H H H   E E E E         H H H
         *  H H H   E E E E         H H H
         *  H H H P P P P P P ... P H H H
         *                    x10
         */
        empty_world.build(BuildingType.HALLS, new Vector2Int(0, 0));
        empty_world.build(BuildingType.HALLS, new Vector2Int(20, 0));
        empty_world.build(BuildingType.LIBRARY, new Vector2Int(4, 1));
        for (int i = 3; i < 20 ; i++) {
            empty_world.build(BuildingType.ROAD, new Vector2Int(i, 0));
        }

        // Left accom satisfaction should be 0.322
        // Right accom satisfaction should be 0.085
        HashSet<Float> expected = new HashSet<>();
        expected.add(0.322f);
        expected.add(0.085f);
        satisfactionSum = GameUtils.updateSatisfactionScore(empty_world);
        System.out.println(satisfactionSum);
        assertEquals(2, gameState.accomSatisfaction.size(), "Verify 2 accommodations.");
        assertClose(0.407f, satisfactionSum, 0.001f, "Sum should be close to 0.441");
        assertClose(expected, new HashSet<>(gameState.accomSatisfaction.values().stream()
                        .map(i -> i.totalSatisfaction).toList()),
                0.001f,
            "Verify the distance impact on the two accommodations.");
        assertClose(0.204f, gameState.targetSatisfaction, 0.001f,
            "Target Satisfaction should be the average of accommodations.");
    }

    @Test
    public void satisfactionAddScore() {
        gameState.gameWorld.build(BuildingType.HALLS, new Vector2Int(0, 0));
        gameState.gameWorld.build(BuildingType.LIBRARY, new Vector2Int(4, 0));
        gameState.gameWorld.build(BuildingType.ROAD, new Vector2Int(3, 0));
        satisfactionSum = GameUtils.updateSatisfactionScore(gameState.gameWorld);
        System.out.println(satisfactionSum);
        float totalTime = 0f;
        float waitTime = 10f; // Wait for 10 seconds
        float deltaTime = 1 / 60f; // Assume 60 FPS for each frame
        while (totalTime < waitTime) {
            // Simulate the passage of time by updating the game loop
            game.getScreen().render(deltaTime); // This triggers the render() method
            totalTime += deltaTime; // Increment the total time by delta
        }
        System.out.println(gameState.totalScore);
        assertEquals(100 + Math.round(satisfactionSum * 100), gameState.totalScore);
    }
}
