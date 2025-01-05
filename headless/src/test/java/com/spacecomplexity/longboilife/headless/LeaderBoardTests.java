package com.spacecomplexity.longboilife.headless;

import com.spacecomplexity.longboilife.menu.leaderboard.LeaderboardElement;
import com.spacecomplexity.longboilife.menu.leaderboard.LeaderboardUtils;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderBoardTests extends AbstractHeadlessGdxTest{

    @Test
    public void leaderboardElementChangeTest() {
        LeaderboardElement leaderboardElement = new LeaderboardElement();
        String expectedName = "Testman";
        int expectedScore = 32;

        leaderboardElement.setName("Testman");
        leaderboardElement.setScore(32);

        assertSame(expectedName, leaderboardElement.getName(),
            "The leaderboardElement's name should have changed correctly");
        assertSame(expectedScore, leaderboardElement.getScore(),
            "The leaderboardElement's name should have changed correctly");
    }

//    @Test
//    public void loadScoreFailsTest() {
//        String testAddress = "fakeLeaderboard.json";
//
//        try (MockedStatic mockedStatic = mockStatic(Gdx.app.getPreferences())) {
//            mockedStatic.when(LeaderboardUtils::getJsonAddress).thenReturn(testAddress);
//            mockedStatic.when(LeaderboardUtils::loadScore).thenCallRealMethod();
//
//            assertThrows(InvalidLeaderboardFileException.class, LeaderboardUtils::loadScore,
//            "loadScore doesn't throw an exception when JSON doesn't exist");
//        }
//    }

//    @Test
//    public void loadScoreSuccessTest() {
//        String testAddress = "src/test/java/com/spacecomplexity/longboilife/headless/leaderboardTestVer.json";
//        LeaderboardElement[] expectedOut = {new LeaderboardElement("Player", 9591), new LeaderboardElement("Adam", 7493),
//            new LeaderboardElement("AdamWooooo", 5303), new LeaderboardElement("test", 101),  new LeaderboardElement("Player", 100)};
//        int expectedOutLength = expectedOut.length;
//
//        try (MockedStatic mockedStatic = mockStatic(LeaderboardUtils.class)) {
//            mockedStatic.when(LeaderboardUtils::getJsonAddress).thenReturn(testAddress);
//            mockedStatic.when(LeaderboardUtils::loadScore ).thenCallRealMethod();
//
//            assertTrue(new ReflectionEquals(expectedOut).matches(LeaderboardUtils.loadScore()), "loadScore loads json data incorrectly");
//        }
//    }

    @Test
    public void addScoreEmptyTest() {
        LeaderboardElement inputElement = new LeaderboardElement("Arthur", 42);
        List<LeaderboardElement> inputList = Collections.emptyList();
        LeaderboardElement[] expectedOut = {inputElement};
        LeaderboardElement[] actualOut = LeaderboardUtils.addScore(inputElement, inputList);

        assertTrue(new ReflectionEquals(expectedOut).matches(actualOut), "addScore fails to add to an empty list correctly");
    }

    @Test
    public void addScorePartialTest() {
        LeaderboardElement inputElementLow = new LeaderboardElement("Arthur", 42);
        LeaderboardElement inputElementMiddle = new LeaderboardElement("Marvin", 73);
        LeaderboardElement inputElementHigh = new LeaderboardElement("Douglas", 164);
        LeaderboardElement[] inputArray = {new LeaderboardElement("Trillian", 150), new LeaderboardElement("Zaphod", 100), new LeaderboardElement("Ford", 50)};
        List<LeaderboardElement> inputList = Arrays.asList(inputArray);

        LeaderboardElement[] expectedOutLow = {new LeaderboardElement("Trillian", 150), new LeaderboardElement("Zaphod", 100), new LeaderboardElement("Ford", 50), inputElementLow};
        LeaderboardElement[] actualOut = LeaderboardUtils.addScore(inputElementLow, inputList);
        assertTrue(new ReflectionEquals(expectedOutLow).matches(actualOut), "addScore fails to add a value to the end of the list");

        LeaderboardElement[] expectedOutMiddle = {new LeaderboardElement("Trillian", 150), new LeaderboardElement("Zaphod", 100), inputElementMiddle, new LeaderboardElement("Ford", 50)};
        actualOut = LeaderboardUtils.addScore(inputElementMiddle, inputList);
        assertTrue(new ReflectionEquals(expectedOutMiddle).matches(actualOut), "addScore fails to add a value to the middle of the list");

        LeaderboardElement[] expectedOutHigh = {inputElementHigh, new LeaderboardElement("Trillian", 150), new LeaderboardElement("Zaphod", 100), new LeaderboardElement("Ford", 50)};
        actualOut = LeaderboardUtils.addScore(inputElementHigh, inputList);
        assertTrue(new ReflectionEquals(expectedOutHigh).matches(actualOut), "addScore fails to add a value to the start of the list");
    }

    @Test
    public void addScoreFullTest() {
        LeaderboardElement inputElementLow = new LeaderboardElement("Arthur", 42);
        LeaderboardElement inputElementHigh = new LeaderboardElement("Douglas", 164);
        LeaderboardElement[] inputArray = {new LeaderboardElement("Trillian", 150), new LeaderboardElement("Cricket", 117), new LeaderboardElement("Zaphod", 100),
                new LeaderboardElement("Marvin", 73), new LeaderboardElement("Ford", 50)};
        List<LeaderboardElement> inputList = Arrays.asList(inputArray);

        // expected out is same as input
        LeaderboardElement[] actualOut = LeaderboardUtils.addScore(inputElementLow, inputList);
        assertTrue(new ReflectionEquals(inputArray).matches(actualOut), "addScore fails to add a value to the end of the list");

        LeaderboardElement[] expectedOut = {inputElementHigh, new LeaderboardElement("Trillian", 150), new LeaderboardElement("Cricket", 117), new LeaderboardElement("Zaphod",
                100), new LeaderboardElement("Marvin", 73)};
        actualOut = LeaderboardUtils.addScore(inputElementHigh, inputList);
        assertTrue(new ReflectionEquals(expectedOut).matches(actualOut), "addScore fails to add a value to the start of the list");
    }
}
