package com.app.agnie.owl.Util;

import android.os.Parcel;

import com.app.agnie.owl.Util.Entities.DictionaryEntry;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class DictionaryEntryTest {

    private DictionaryEntry testEntry;
    private DictionaryEntry testFromParcel;


    @Before
    public void beforeTest() {
        byte[] image = {1, 2, 3, 4, 5, 6, 7, 8, 15, 100, 2, 3, 4, 5};
        ArrayList<String> testSentences = new ArrayList<>();
        testSentences.add("hey you");
        ArrayList<String> testSentencesTranslations = new ArrayList<>();
        testSentencesTranslations.add("hey ty");
        ArrayList<String> testSentencesSounds = new ArrayList<>();
        testSentencesSounds.add("http://you.may.find/sounds/here");
        testEntry = new DictionaryEntry(1, image, "some caption", "some sound", "translates to", testSentences, testSentencesTranslations, testSentencesSounds);

        Parcel mockedParcel = Parcel.obtain();
        testEntry.writeToParcel(mockedParcel, testEntry.describeContents());
        mockedParcel.setDataPosition(0);

        testFromParcel = DictionaryEntry.CREATOR.createFromParcel(mockedParcel);
    }

    @Test
    public void writeToParcel() throws Exception {
        checkId(testEntry, testFromParcel);
        checkCaption(testEntry, testFromParcel);
        checkSound(testEntry, testFromParcel);
        checkTranslation(testEntry, testFromParcel);
        checkSentence(testEntry, testFromParcel);
        checkSentenceTranslation(testEntry, testFromParcel);
        checkSentenceSound(testEntry, testFromParcel);
    }

    private void checkId(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getId(), entry2.getId());
    }

    private void checkCaption(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getCaption(), entry2.getCaption());
    }

    private void checkSound(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getCaptionSound(), entry2.getCaptionSound());
    }

    private void checkTranslation(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getCaptionTranslation(), entry2.getCaptionTranslation());
    }

    private void checkSentence(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getExampleSentences(), entry2.getExampleSentences());
    }

    private void checkSentenceTranslation(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getExampleSentenceTranslations(), entry2.getExampleSentenceTranslations());
    }

    private void checkSentenceSound(DictionaryEntry entry1, DictionaryEntry entry2) {
        assertEquals(entry1.getExampleSentenceSounds(), entry2.getExampleSentenceSounds());
    }

}