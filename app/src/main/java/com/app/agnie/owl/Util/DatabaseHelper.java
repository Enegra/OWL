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
    public static final String COLUMN_WORD_DESCRIPTION = "word_description";
    public static final String COLUMN_WORD_ID = "word_id";

    public static final String TABLE_SENTENCE = "sentence";
    public static final String COLUMN_SENTENCE = "sentence";


    private static final String DATABASE_NAME = "owl_content.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //// TODO: 3/6/2017 Decide about the entities in the database and make strings for creating them

    @Override
    public void onCreate(SQLiteDatabase database) {
        createWord(database);
        createLanguage(database);
        createWordDescription(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //// TODO: 3/12/2017
    }

    private void createWord(SQLiteDatabase database){
        String createWord = "create table if not exists " + TABLE_WORD + "(" + COLUMN_ID + " integer primary key autoincrement,  " + COLUMN_PICTURE + " text not null);";
        database.execSQL(createWord);
    }

    private void createLanguage(SQLiteDatabase database){
        String createLanguage = "create table if not exists " + TABLE_LANGUAGE + "(" + COLUMN_LANGUAGE + " text primary key);";
        database.execSQL(createLanguage);
    }
    
    private void createWordDescription(SQLiteDatabase database){
        String createWordDescription = "create table if not exists " + TABLE_WORD_DESCRIPTION + "(" + COLUMN_WORD_DESCRIPTION + " text, " + COLUMN_WORD_ID + " integer, " + COLUMN_LANGUAGE + " text, foreign key (" +COLUMN_WORD_ID + ") references " + TABLE_WORD + "(" + COLUMN_ID + "), foreign key (" + COLUMN_LANGUAGE +") references " + TABLE_LANGUAGE + "( " + COLUMN_LANGUAGE + ") );";
        database.execSQL(createWordDescription);
    }

}
