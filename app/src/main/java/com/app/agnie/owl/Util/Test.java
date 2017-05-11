package com.app.agnie.owl.Util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Test implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.language);
        dest.writeString(this.caption);
        dest.writeString(this.description);
        dest.writeList(this.questions);
        dest.writeList(this.answers);
    }

    protected Test(Parcel in) {
        this.language = in.readString();
        this.caption = in.readString();
        this.description = in.readString();
        this.questions = new ArrayList<Question>();
        in.readList(this.questions, Question.class.getClassLoader());
        this.answers = new ArrayList<Answer>();
        in.readList(this.answers, Answer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
