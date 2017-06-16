package com.app.agnie.owl.Util.Entities;

import static android.R.attr.id;

public class Answer {

    private String content;
    private boolean isCorrect;


    public Answer(String content, boolean isCorrect) {
        this.content = content;
        this.isCorrect = isCorrect;
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

}
