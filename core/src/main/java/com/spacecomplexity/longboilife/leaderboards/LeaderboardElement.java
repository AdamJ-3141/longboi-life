package com.spacecomplexity.longboilife.leaderboards;

public class LeaderboardElement {
    private String name;
    private int score;

    public LeaderboardElement(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public LeaderboardElement() {
        this.name = null;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
