package com.app.agnie.owl.Util;

import java.util.ArrayList;

/**
 * Created by agnie on 5/7/2017.
 */

public class Test {

    private String language;
    private String caption;
    private String description;
    private ArrayList<Question> questions;
    private ArrayList<Answer> answers;

    public Test(String language, String title, String description) {
        this.language = language;
        this.caption = title;
        this.description = description;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public String getLanguage() {
        return language;
    }

    public String getCaption() {
        return caption;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }
}
