package com.app.agnie.owl.Util;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;



public class DictionaryEntry implements Parcelable {

    private int id;
    private byte[] imageContent;
    private String caption;
    private String captionTranslation;
    private ArrayList<String> exampleSentences;
    private ArrayList<String> exampleSentenceTranslations;

    public DictionaryEntry(int id, byte[] imageContent, String caption, String captionTranslation, ArrayList<String> exampleSentences, ArrayList<String> exampleSentenceTranslations) {
        this.id = id;
        this.imageContent = imageContent;
        this.caption = caption;
        this.captionTranslation = captionTranslation;
        this.exampleSentences = exampleSentences;
        this.exampleSentenceTranslations = exampleSentenceTranslations;
    }

    public DictionaryEntry(int id, byte[] imageContent) {
        this.id = id;
        this.imageContent = imageContent;
        this.exampleSentences = new ArrayList<>();
        this.exampleSentenceTranslations = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public byte[] getImageContent() {
        return imageContent;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaptionTranslation() {
        return captionTranslation;
    }

    public void setCaptionTranslation(String captionTranslation) {
        this.captionTranslation = captionTranslation;
    }

    public ArrayList<String> getExampleSentences() {
        return exampleSentences;
    }

    public void setSentence(String sentence) {
        exampleSentences.add(sentence);
    }

    public ArrayList<String> getExampleSentenceTranslations() {
        return exampleSentenceTranslations;
    }

    public void setSentenceTranslation(String sentenceTranslation) {
        exampleSentenceTranslations.add(sentenceTranslation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByteArray(this.imageContent);
        dest.writeString(this.caption);
        dest.writeString(this.captionTranslation);
        dest.writeStringList(this.exampleSentences);
        dest.writeStringList(this.exampleSentenceTranslations);
    }

    protected DictionaryEntry(Parcel in) {
        this.id = in.readInt();
        this.imageContent = in.createByteArray();
        this.caption = in.readString();
        this.captionTranslation = in.readString();
        this.exampleSentences = in.createStringArrayList();
        this.exampleSentenceTranslations = in.createStringArrayList();
    }

    public static final Parcelable.Creator<DictionaryEntry> CREATOR = new Parcelable.Creator<DictionaryEntry>() {
        @Override
        public DictionaryEntry createFromParcel(Parcel source) {
            return new DictionaryEntry(source);
        }

        @Override
        public DictionaryEntry[] newArray(int size) {
            return new DictionaryEntry[size];
        }
    };
}
