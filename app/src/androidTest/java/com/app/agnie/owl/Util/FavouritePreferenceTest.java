package com.app.agnie.owl.Util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.app.agnie.owl.Util.Entities.DictionaryEntry;
import com.app.agnie.owl.Util.Entities.Lesson;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class FavouritePreferenceTest {

    private Context context;
    private FavouritePreference favouritePreference;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getContext();
    }

    @Test
    public void addFavouriteWord() throws Exception {
        byte[] image = {1, 2, 3, 4, 5, 6, 7, 8, 15, 100, 2, 3, 4, 5};
        ArrayList<String> testSentences = new ArrayList<>();
        ArrayList<String> testSentencesTranslations = new ArrayList<>();
        ArrayList<String> testSentencesSounds = new ArrayList<>();
        DictionaryEntry testEntry = new DictionaryEntry(1, image, "some caption", "some sound", "translates to", testSentences, testSentencesTranslations, testSentencesSounds);
        DictionaryEntry testEntry2 = new DictionaryEntry(2, image, "blarg", "blurg", "blorg", testSentences, testSentencesTranslations, testSentencesSounds);
        favouritePreference = new FavouritePreference();
        favouritePreference.addFavouriteWord(context, testEntry);
        assertTrue(favouritePreference.contains(context, testEntry));
        assertFalse(favouritePreference.contains(context, testEntry2));
    }

    @Test
    public void addFavouriteLesson() throws Exception {
        Lesson testLesson = new Lesson(1, "English", "Polish", "Lorem ipsum", "dolor sit amet", "consectetur adipiscing elit");
        favouritePreference = new FavouritePreference();
        favouritePreference.addFavouriteLesson(context, testLesson);
        assertTrue(favouritePreference.contains(context, testLesson));
    }

    @Test
    public void removeFavouriteWord() throws Exception {
        byte[] image = {1, 2, 3, 4, 5, 6, 7, 8, 15, 100, 2, 3, 4, 5};
        ArrayList<String> testSentences = new ArrayList<>();
        ArrayList<String> testSentencesTranslations = new ArrayList<>();
        ArrayList<String> testSentencesSounds = new ArrayList<>();
        DictionaryEntry testEntry = new DictionaryEntry(1, image, "some caption", "some sound", "translates to", testSentences, testSentencesTranslations, testSentencesSounds);
        favouritePreference = new FavouritePreference();
        favouritePreference.addFavouriteWord(context, testEntry);
        favouritePreference.removeFavouriteWord(context, testEntry);
        assertFalse(favouritePreference.contains(context, testEntry));
    }

    @Test
    public void removeFavouriteLesson() throws Exception {
        Lesson testLesson = new Lesson(1, "English", "Polish", "Lorem ipsum", "dolor sit amet", "consectetur adipiscing elit");
        favouritePreference = new FavouritePreference();
        favouritePreference.addFavouriteLesson(context, testLesson);
        assertTrue(favouritePreference.contains(context, testLesson));
        favouritePreference.removeFavouriteLesson(context, testLesson);
        assertFalse(favouritePreference.contains(context, testLesson));
    }

}