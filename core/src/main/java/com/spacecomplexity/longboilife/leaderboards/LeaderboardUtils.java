package com.spacecomplexity.longboilife.leaderboards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderboardUtils {
    private static Json json = new Json();
    private static final int maxLeaderboardSize = 5;

    public static LeaderboardElement[] loadScore(){
        FileHandle leaderboard = Gdx.files.local("leaderboard.json");
        if(leaderboard.exists()){
            String scoreStr = leaderboard.readString();
            LeaderboardElement[] scores = json.fromJson(LeaderboardElement[].class, scoreStr);
            return scores;
        }
        throw new InvalidLeaderboardFileException("Couldn't load leaderboard");
    }

    public static void addScore(LeaderboardElement score){
        List<LeaderboardElement> scores = Arrays.asList(loadScore());
        List<LeaderboardElement> scoresList = new ArrayList<>(scores);
        int findScore = score.getScore();
        boolean added = false;
        for (int i = 0; i < scores.size() ; i++) {
            System.out.println(scores.get(i).getScore());
            if (scores.get(i).getScore() < findScore) {
                scoresList.add(i, score);
                added = true;
                break;
            }
        }
        if (!added) {
            scoresList.add(score);
        }
        if (scoresList.size() > maxLeaderboardSize) {
            scoresList.remove(scoresList.size() - 1);
        }
        scoreSave(scoresList.toArray(new LeaderboardElement[scores.size()]));
    }

    private static void scoreSave(LeaderboardElement[] score) {
        String scoreStr = json.toJson(score);
        FileHandle leaderboard = Gdx.files.local("leaderboard.json");
        leaderboard.writeString(scoreStr ,false,"UTF-8");
    }
}
