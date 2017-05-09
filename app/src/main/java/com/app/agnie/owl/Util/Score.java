package com.app.agnie.owl.Util;

public class Score {

    private String caption;
    private int maxScore;
    private int achievedScore;
    private int testId;
    private String scoreDate;

    public Score(String title, int maxScore, int achievedScore, int testId, String scoreDate) {
        this.caption = title;
        this.maxScore = maxScore;
        this.achievedScore = achievedScore;
        this.testId = testId;
        this.scoreDate = scoreDate;
    }

    public String getCaption() {
        return caption;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getAchievedScore() {
        return achievedScore;
    }

    public int getTestId() {
        return testId;
    }

    public String getScoreDate() {
        return scoreDate;
    }
}
