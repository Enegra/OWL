package com.app.agnie.owl.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by agnie on 3/6/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_WORD = "word";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PICTURE = "picture_name";

    public static final String TABLE_LANGUAGE = "language";
    public static final String COLUMN_LANGUAGE = "language_name";

    public static final String TABLE_WORD_DESCRIPTION = "word_description";
    public static final String COLUMN_WORD = "word";

    public static final String TABLE_SENTENCE = "sentence";
    public static final String COLUMN_SENTENCE = "sentence";


    private static final String DATABASE_NAME = "owl_content.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //// TODO: 3/6/2017 Decide about the entities in the database and make strings for creating them

    public String createWord(){
        return "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
