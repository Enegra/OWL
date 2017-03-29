package com.app.agnie.owl.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class FavouritePreference {

    private static final String PREFERENCES_NAME = "OWL";
    private static final String FAVOURITES = "Favourite_Words";

    public FavouritePreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavourites(Context context, ArrayList<DictionaryEntry> favourites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favourites);
        editor.putString(FAVOURITES, jsonFavorites);
        editor.apply();
    }

    public void addFavourite(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favorites = getFavourites(context);
        if (favorites == null)
            favorites = new ArrayList<>();
        favorites.add(entry);
        saveFavourites(context, favorites);
    }

    public void removeFavourite(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favourites = getFavourites(context);
        if (favourites != null) {
            for (DictionaryEntry favourite : favourites){
                if (favourite.getImage().equals(entry.getImage())){
                    favourites.remove(favourite);
                }
            }
            saveFavourites(context, favourites);
        }
    }

    public ArrayList<DictionaryEntry> getFavourites(Context context) {
        SharedPreferences settings;
        ArrayList<DictionaryEntry> favourites;

        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVOURITES)) {
            String jsonFavorites = settings.getString(FAVOURITES, null);
            Gson gson = new Gson();
            DictionaryEntry[] favoriteItems = gson.fromJson(jsonFavorites,
                    DictionaryEntry[].class);
            favourites = new ArrayList<>(Arrays.asList(favoriteItems));
        } else {
            return null;
        }
        return favourites;
    }

    public boolean contains(Context context, DictionaryEntry entry){
        ArrayList<DictionaryEntry> favourites = getFavourites(context);
        if (favourites!=null && !favourites.isEmpty()){
            for (DictionaryEntry favourite : favourites){
                if (favourite.getImage().equals(entry.getImage())){
                    return true;
                }
            }
        }
        return false;
    }

}