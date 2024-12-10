package com.spacecomplexity.longboilife.leaderboards;

/**
 * Class that represents the rows of the leaderboard
 */
public class LeaderboardElement {
    private String name;
    private int score;

    /**
     * creates an object with the attributes assigned to the values passed
     * @param name  value of name attribute
     * @param score value of score attribute
     */
    public LeaderboardElement(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * creates an empty object with no name and score
     */
    public LeaderboardElement() {
        this.name = null;
        this.score = 0;
    }

    /**
     * @return the name attribute
     */
    public String getName() {
        return name;
    }

    /**
     * @return the score attribute
     */
    public int getScore() {
        return score;
    }

    /**
     * @param name  sets the name attribute to this
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param score sets the score attribute to this
     */
    public void setScore(int score) {
        this.score = score;
    }
}
