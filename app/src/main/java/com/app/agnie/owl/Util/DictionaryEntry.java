package com.app.agnie.owl.Util;

/**
 * Created by agnie on 2/21/2017.
 */

public class DictionaryEntry {

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

    public String getImage() {
        return image;
    }

    public String getCaption() {
        return caption;
    }

    public String getCaptionTranslation() {
        return captionTranslation;
    }

    public String[] getExampleSentences() {
        return exampleSentences;
    }

    public String[] getExampleSentenceTranslations() {
        return exampleSentenceTranslations;
    }

}
