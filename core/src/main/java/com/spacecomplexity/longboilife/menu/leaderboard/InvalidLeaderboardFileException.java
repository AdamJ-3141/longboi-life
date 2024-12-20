package com.spacecomplexity.longboilife.menu.leaderboard;

/**
 * An exception for if there is an issue loading the leaderboard
 */
public class InvalidLeaderboardFileException extends RuntimeException {
    public InvalidLeaderboardFileException(String message) {
        super(message);
    }
}
