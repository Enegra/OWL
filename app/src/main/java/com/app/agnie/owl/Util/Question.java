package com.app.agnie.owl.Util;

/**
 * Created by agnie on 5/7/2017.
 */

public class Question {

    private int id;
    private String content;
    private int testId;

    public Question(int id, String content, int testId) {
        this.id = id;
        this.content = content;
        this.testId = testId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getTestId() {
        return testId;
    }

}
