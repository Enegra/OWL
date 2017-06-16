package com.app.agnie.owl.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.agnie.owl.Util.Entities.DictionaryEntry;
import com.app.agnie.owl.Util.Entities.Score;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ScorePreference {

    private static final String PREFERENCES_NAME = "OWL";
    private static final String SAVED_SCORES = "Saved_Scores";

    private void saveScores(Context context, ArrayList<Score> scores) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonScores = gson.toJson(scores);
        editor.putString(SAVED_SCORES, jsonScores);
        editor.apply();
    }

    public void addScore(Context context, Score score) {
        ArrayList<Score> scores = getScores(context);
        scores.add(score);
        saveScores(context, scores);
    }

    public void clearScores(Context context, DictionaryEntry entry) {
        ArrayList<Score> emptyScores = new ArrayList<>();
        saveScores(context, emptyScores);
    }

    public void removeScore(Context context, Score scoreToRemove) {
        ArrayList<Score> scores = getScores(context);
        ArrayList<Score> newScores = new ArrayList<>();
        for (Score score : scores) {
            if (!score.equals(scoreToRemove)) {
                newScores.add(score);
            }
        }
        saveScores(context, newScores);
    }


    public ArrayList<Score> getScores(Context context) {
        SharedPreferences settings;
        ArrayList<Score> scores;

        settings = context.getSharedPreferences(PREFERENCES_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SAVED_SCORES)) {
            String jsonScores = settings.getString(SAVED_SCORES, null);
            Gson gson = new Gson();
            Score[] scoreItems = gson.fromJson(jsonScores,
                    Score[].class);
            scores = new ArrayList<>(Arrays.asList(scoreItems));
        } else {
            return new ArrayList<>();
        }
        return scores;
    }

}
