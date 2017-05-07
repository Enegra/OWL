package com.app.agnie.owl.Util;

/**
 * Created by agnie on 5/7/2017.
 */

public class Answer {

    private int id;
    private String content;
    private boolean isCorrect;
    private int questionId;
    private int testId;

    public Answer(int id, String content, boolean isCorrect, int questionId, int testId) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
        this.testId = testId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getTestId() {
        return testId;
    }
}
