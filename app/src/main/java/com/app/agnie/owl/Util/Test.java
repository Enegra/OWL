package com.app.agnie.owl.Util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Test implements Parcelable {

    private String language;
    private String caption;
    private String description;
    private String textContent;
    private ArrayList<Question> questions;

    public Test(String language, String title, String description, String textContent) {
        this.language = language;
        this.caption = title;
        this.description = description;
        this.textContent = textContent;
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question){
        questions.add(question);
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

    public String getTextContent(){
        return textContent;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
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
        dest.writeString(this.textContent);
        dest.writeList(this.questions);
    }

    protected Test(Parcel in) {
        this.language = in.readString();
        this.caption = in.readString();
        this.description = in.readString();
        this.textContent = in.readString();
        this.questions = new ArrayList<Question>();
        in.readList(this.questions, Question.class.getClassLoader());
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
