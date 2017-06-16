package com.app.agnie.owl.Util.Entities;

public class Score {

    private String caption;
    private int maxScore;
    private int achievedScore;
    private String testLanguage;
    private String scoreDate;

    public Score(String title, int maxScore, int achievedScore, String testLanguage, String scoreDate) {
        this.caption = title;
        this.maxScore = maxScore;
        this.achievedScore = achievedScore;
        this.testLanguage = testLanguage;
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

    public String getTestLanguage(){
        return testLanguage;
    }

    public String getScoreDate() {
        return scoreDate;
    }

    public boolean equals(Score score){
        if (!this.caption.equals(score.caption)){
            return false;
        }
        if (!(this.maxScore == score.maxScore)){
            return false;
        }
        if (!(this.achievedScore == score.achievedScore)){
            return false;
        }
        if (!this.testLanguage.equals(score.testLanguage)){
            return false;
        }
        if (!this.scoreDate.equals(score.scoreDate)){
            return false;
        }
        return true;
    }
}
