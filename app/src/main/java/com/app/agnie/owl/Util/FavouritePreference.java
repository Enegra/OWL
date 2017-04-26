package com.app.agnie.owl.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class FavouritePreference {

    private static final String PREFERENCES_NAME = "OWL";
    private static final String FAVOURITE_WORDS = "Favourite_Words";
    private static final String FAVOURITE_LESSONS = "Favourite_Lessons";

    public void saveFavouriteWords(Context context, ArrayList<DictionaryEntry> favourites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favourites);
        editor.putString(FAVOURITE_WORDS, jsonFavorites);
        editor.apply();
    }

    public void saveFavouriteLessons(Context context, ArrayList<Lesson> favourites){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favourites);
        editor.putString(FAVOURITE_LESSONS, jsonFavorites);
        editor.apply();
    }

    public void addFavouriteWord(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favorites = getFavouriteWords(context);
        favorites.add(entry);
        saveFavouriteWords(context, favorites);
    }

    public void addFavouriteLesson(Context context, Lesson entry) {
        ArrayList<Lesson> favorites = getFavouriteLessons(context);
        favorites.add(entry);
        saveFavouriteLessons(context, favorites);
    }

    public void removeFavouriteWord(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favourites = getFavouriteWords(context);
        ArrayList<DictionaryEntry> newFavourites = new ArrayList<>();
        for (DictionaryEntry favourite : favourites) {
            if (favourite.getId() != entry.getId()) {
                newFavourites.add(favourite);
            }
        }
        saveFavouriteWords(context, newFavourites);
    }

    public void removeFavouriteLesson(Context context, Lesson entry) {
        ArrayList<Lesson> favourites = getFavouriteLessons(context);
        ArrayList<Lesson> newFavourites = new ArrayList<>();
        for (Lesson favourite : favourites) {
            if (favourite.getId() != entry.getId()) {
                newFavourites.add(favourite);
            }
        }
        saveFavouriteLessons(context, newFavourites);
    }

    public ArrayList<DictionaryEntry> getFavouriteWords(Context context) {
        SharedPreferences settings;
        ArrayList<DictionaryEntry> favourites;

        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVOURITE_WORDS)) {
            String jsonFavorites = settings.getString(FAVOURITE_WORDS, null);
            Gson gson = new Gson();
            DictionaryEntry[] favoriteItems = gson.fromJson(jsonFavorites,
                    DictionaryEntry[].class);
            favourites = new ArrayList<>(Arrays.asList(favoriteItems));
        } else {
            return new ArrayList<>();
        }
        return favourites;
    }

    public ArrayList<Lesson> getFavouriteLessons(Context context) {
        SharedPreferences settings;
        ArrayList<Lesson> favourites;

        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVOURITE_LESSONS)) {
            String jsonFavorites = settings.getString(FAVOURITE_LESSONS, null);
            Gson gson = new Gson();
            Lesson[] favoriteItems = gson.fromJson(jsonFavorites,
                    Lesson[].class);
            favourites = new ArrayList<>(Arrays.asList(favoriteItems));
        } else {
            return new ArrayList<>();
        }
        return favourites;
    }

    public boolean contains(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favourites = getFavouriteWords(context);
        for (DictionaryEntry favourite : favourites) {
            if (favourite.getId() == entry.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Context context, Lesson entry) {
        ArrayList<Lesson> favourites = getFavouriteLessons(context);
        for (Lesson favourite : favourites) {
            if (favourite.getId() == entry.getId()) {
                return true;
            }
        }
        return false;
    }


}
