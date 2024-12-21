package com.spacecomplexity.longboilife.headless;

import com.badlogic.gdx.Gdx;
import com.spacecomplexity.longboilife.menu.leaderboard.InvalidLeaderboardFileException;
import com.spacecomplexity.longboilife.menu.leaderboard.LeaderboardElement;
import com.spacecomplexity.longboilife.menu.leaderboard.LeaderboardUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaderBoardTests extends AbstractHeadlessGdxTest{
    @Test
    public void leaderboardExists() {
        assertTrue(Gdx.files.internal("leaderboard.json").exists(),
            "Greggs texture exists");
    }

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

    @Test
    public void loadScoreFailsTest() {
        String testAddress = "fakeLeaderboard.json";

        try (MockedStatic mockedStatic = mockStatic(LeaderboardUtils.class)) {
            mockedStatic.when(LeaderboardUtils::getJsonAddress).thenReturn(testAddress);
            mockedStatic.when(LeaderboardUtils::loadScore).thenCallRealMethod();

            assertThrows(InvalidLeaderboardFileException.class, LeaderboardUtils::loadScore,
            "loadScore doesn't throw an exception when JSON doesn't exist");
        }
    }

    @Test
    public void loadScoreSuccessTest() {
        String testAddress = "src/test/java/com/spacecomplexity/longboilife/headless/leaderboardTestVer.json";
        LeaderboardElement[] expectedOut = {new LeaderboardElement("Player", 9591), new LeaderboardElement("Adam", 7493),
            new LeaderboardElement("AdamWooooo", 5303), new LeaderboardElement("test", 101),  new LeaderboardElement("Player", 100)};
        int expectedOutLength = expectedOut.length;

        try (MockedStatic mockedStatic = mockStatic(LeaderboardUtils.class)) {
            mockedStatic.when(LeaderboardUtils::getJsonAddress).thenReturn(testAddress);
            mockedStatic.when(LeaderboardUtils::loadScore ).thenCallRealMethod();

            assertTrue(new ReflectionEquals(expectedOut).matches(LeaderboardUtils.loadScore()), "loadScore loads json data incorrectly");
        }
    }
}
