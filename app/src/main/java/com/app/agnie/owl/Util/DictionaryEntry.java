package com.app.agnie.owl.Util;

/**
 * Created by agnie on 2/21/2017.
 */

public class DictionaryEntry {

    private int id;
    private String image;
    private String caption;
    private String captionTranslation;
    private String[] exampleSentences;
    private String[] exampleSentenceTranslations;

    public DictionaryEntry(String image, String caption, String captionTranslation, String[] exampleSentences, String[] exampleSentenceTranslations){
        this.image = image;
        this.caption = caption;
        this.captionTranslation = captionTranslation;
        this.exampleSentences = exampleSentences;
        this.exampleSentenceTranslations = exampleSentenceTranslations;
    }

    public DictionaryEntry(int id, String image, String caption, String captionTranslation, String[] exampleSentences, String[] exampleSentenceTranslations){
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

    public String[] getExampleSentences() {
        return exampleSentences;
    }

    public void setSentence(String sentence){
        //todo
    }

    public String[] getExampleSentenceTranslations() {
        return exampleSentenceTranslations;
    }

    public void setSentenceTranslation(String sentenceTranslation){
        //todo
    }

}
