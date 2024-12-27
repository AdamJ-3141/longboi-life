package com.spacecomplexity.longboilife.game.utils;

import com.spacecomplexity.longboilife.game.achievement.AchievementType;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.globals.MainTimer;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    private static ArrayList<AchievementType> possibleAchievements = new ArrayList<>();
    private static ArrayList<AchievementType> completeAchievements = new ArrayList<>();
    private static long lastAchievementTime;
    public final static int achievementLiveTime = 10; // in seconds

    /**
     * checks if the achievements yet to be completed have been forfilled. If they are they are moved to the completeAchievements Array
     */
    public static void checkAchievements() {
        for (int i = possibleAchievements.size()-1; i >= 0; i--) {
            AchievementType currentAchievement = possibleAchievements.get(i);

            boolean achieved = currentAchievement.isAchieved();
            if (achieved) {
                //implement score changes
                GameState.getState().totalScore += currentAchievement.scoreChange;

                // updates the gamestate to have the current achievement
                GameState.getState().currentAchievement = currentAchievement;

                // sets the time since last
                lastAchievementTime = MainTimer.getTimerManager().getTimer().getTimeLeft();

                completeAchievements.add(currentAchievement);
                possibleAchievements.remove(currentAchievement);
            }
        }
    }

    /**
     * this method will clear the current achievement it has been active for the max time determined by achievementLiveTime
     */
    public static void updateAchievements() {
        if (GameState.getState().currentAchievement != null) {
            if (lastAchievementTime - MainTimer.getTimerManager().getTimer().getTimeLeft() > achievementLiveTime * 1000) {
                GameState.getState().currentAchievement = null;
            }
        }
    }

    public static ArrayList<AchievementType> getCompleteAchievements() {
        return completeAchievements;
    }

    public static ArrayList<AchievementType> getPossibleAchievements() {
        return possibleAchievements;
    }

    public static void reset() {
        possibleAchievements = new ArrayList<>(List.of(AchievementType.values()));
        completeAchievements.clear();
    }
}
