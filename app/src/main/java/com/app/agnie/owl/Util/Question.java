package com.app.agnie.owl.Util;

import java.util.ArrayList;

import static android.R.attr.id;

public class Question {

    private String content;
    private ArrayList<Answer> answers;
    private int correctAnswerCount;

    public Question(String content) {
        this.content = content;
        answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void addAnswer(Answer answer){
        if (answer.isCorrect()){
            correctAnswerCount++;
        }
        answers.add(answer);

    }

    public void addAnswers(ArrayList<Answer> answers){
        for (Answer answer : answers){
            if (answer.isCorrect()){
                correctAnswerCount++;
            }
            this.answers.add(answer);
        }
    }

    public int getCorrectAnswerCount(){
        return correctAnswerCount;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

}
