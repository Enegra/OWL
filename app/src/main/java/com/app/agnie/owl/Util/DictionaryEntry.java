package com.app.agnie.owl.Util;

import java.util.ArrayList;

/**
 * Created by agnie on 2/21/2017.
 */

public class DictionaryEntry {

    private int id;
    private String image;
    private String caption;
    private String captionTranslation;
    private ArrayList<String> exampleSentences;
    private ArrayList<String> exampleSentenceTranslations;

    public DictionaryEntry(String image, String caption, String captionTranslation, ArrayList<String> exampleSentences, ArrayList<String> exampleSentenceTranslations){
        this.image = image;
        this.caption = caption;
        this.captionTranslation = captionTranslation;
        this.exampleSentences = exampleSentences;
        this.exampleSentenceTranslations = exampleSentenceTranslations;
    }

    public DictionaryEntry(int id, String image, String caption, String captionTranslation, ArrayList<String> exampleSentences, ArrayList<String> exampleSentenceTranslations){
        this.id = id;
        this.image = image;
        this.caption = caption;
        this.captionTranslation = captionTranslation;
        this.exampleSentences = exampleSentences;
        this.exampleSentenceTranslations = exampleSentenceTranslations;
    }

    public DictionaryEntry(int id, String image){
        this.id = id;
        this.image = image;
        this.exampleSentences = new ArrayList<>();
        this.exampleSentenceTranslations = new ArrayList<>();
    }

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption){
        this.caption = caption;
    }

    public String getCaptionTranslation() {
        return captionTranslation;
    }

    public void setCaptionTranslation(String captionTranslation){
        this.captionTranslation=captionTranslation;
    }

    public ArrayList<String> getExampleSentences() {
        return exampleSentences;
    }

    public void setSentence(String sentence){
        exampleSentences.add(sentence);
    }

    public ArrayList<String> getExampleSentenceTranslations() {
        return exampleSentenceTranslations;
    }

    public void setSentenceTranslation(String sentenceTranslation){
        exampleSentenceTranslations.add(sentenceTranslation);
    }

}
