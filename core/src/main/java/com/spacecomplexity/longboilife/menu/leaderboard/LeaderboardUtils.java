package com.spacecomplexity.longboilife.menu.leaderboard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class that contains static methods to manage the json file that contains the Leaderboard
 */
public class LeaderboardUtils {
    private static Json json = new Json();
    private static Preferences preferences = Gdx.app.getPreferences("leaderboard");

    private static final String LEADERBOARD_KEY = "LEADERBOARD_KEY";

    private static final int MAXSIZE = 5; // size of leaderboard

    /**
     * This method loads the leaderboard from the json file
     * @return  Array of type LeaderboardElements which represents the leaderboard
     */
    public static LeaderboardElement[] loadScore(){
        // obtains the json string
        String scoreStr = preferences.getString(LEADERBOARD_KEY, null);

        // if empty then return an empty list
        if (scoreStr == null) {
            return new LeaderboardElement[0];
        }

        //convert string into array of LeaderboardElements
        return json.fromJson(LeaderboardElement[].class, scoreStr);
    }

    /**
     * Adds the score passed to the method to the correct place on the leaderboard
     * @param score LeaderboardElement object to be added
     */
    public static void addScore(LeaderboardElement score){
        // Gets the leaderboard Array and makes it an ArrayList for manipulation
        List<LeaderboardElement> scores = Arrays.asList(loadScore());
        List<LeaderboardElement> scoresList = new ArrayList<>(scores);

        // inserts the LeaderboardElement in the correct location in the list
        int findScore = score.getScore();
        boolean added = false;
        for (int i = 0; i < scores.size() ; i++) {
            if (scores.get(i).getScore() < findScore) {
                scoresList.add(i, score);
                added = true;
                break;
            }
        }
        // if not found add onto the end
        if (!added) {
            scoresList.add(score);
        }

        // if the size of the leaderboard exceeds the max then remove the lowest score
        if (scoresList.size() > MAXSIZE) {
            scoresList.remove(scoresList.size() - 1);
        }

        // update the json file
        scoreSave(scoresList.toArray(new LeaderboardElement[scores.size()]));
    }

    /**
     * private method that updates the json file containing the leaderboard
     * @param scores LeaderboardElements array to be added
     */
    public static void scoreSave(LeaderboardElement[] scores) {
        String jsonString = json.toJson(scores);

        // saves the json file in the preferences part of libgdx
        preferences.putString(LEADERBOARD_KEY, jsonString);
        preferences.flush();
    }
}
