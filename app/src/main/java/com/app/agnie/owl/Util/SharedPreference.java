package com.app.agnie.owl.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by agnie on 3/23/2017.
 */

public class SharedPreference {

    public static final String PREFERENCES_NAME = "OWL";
    public static final String FAVOURITES = "Favourite_Words";

    public SharedPreference() {
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

    public void addFavorite(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favourites = getFavorites(context);
        if (favourites == null)
            favourites = new ArrayList<>();
        favourites.add(entry);
        saveFavourites(context, favourites);
    }

    public void removeFavorite(Context context, DictionaryEntry entry) {
        ArrayList<DictionaryEntry> favourites = getFavorites(context);
        if (favourites != null) {
            favourites.remove(entry);
            saveFavourites(context, favourites);
        }
    }

    public ArrayList<DictionaryEntry> getFavorites(Context context) {
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

}
