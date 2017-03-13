package com.app.agnie.owl.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by agnie on 3/12/2017.
 */

public class DictionaryDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public DictionaryDataSource(Context context){
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public void addCategory(int category){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CATEGORY, category);
        long insertID = database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
        System.out.println(insertID);
    }

    public void addWord(String wordPicture){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PICTURE, wordPicture);
        long insertID = database.insert(DatabaseHelper.TABLE_WORD, null, values);
        System.out.println(insertID);
    }
    
    public void addLanguage(String languageName){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LANGUAGE, languageName);
        long insertID = database.insert(DatabaseHelper.TABLE_LANGUAGE, null, values);
        System.out.println(insertID);
    }

    public void addWordDescription(String description, int wordID, String language){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_WORD_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_WORD_ID, wordID);
        values.put(DatabaseHelper.COLUMN_LANGUAGE, language);
        long insertID = database.insert(DatabaseHelper.TABLE_WORD_DESCRIPTION, null, values);
        System.out.println(insertID);
    }

    public void addSentence(String sentence, int wordID, String language){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SENTENCE, sentence);
        values.put(DatabaseHelper.COLUMN_WORD_ID, wordID);
        values.put(DatabaseHelper.COLUMN_LANGUAGE, language);
        long insertID = database.insert(DatabaseHelper.TABLE_SENTENCE, null, values);
        System.out.println(insertID);
    }

    public void createInitialValues(){
        addWord("mug.png");
        addLanguage("polish");
        addLanguage("english");
        addLanguage("german");
        addWordDescription("kubek", 0, "polish");
        addWordDescription("mug", 0, "english");
        addSentence("Ten kubek jest brudny", 0, "polish");
        addSentence("Mój ulubiony kubek jest niebieski", 0, "polish");
        addSentence("Zbiłeś mój kubek!", 0, "polish");
        addSentence("This mug is dirty", 0, "english");
        addSentence("My favourite mug is blue", 0, "english");
        addSentence("You broke my mug!", 0, "english");
        addWord("tea.png");
        addWordDescription("herbata", 1, "polish");
        addWordDescription("tea", 1, "english");
        addSentence("Mam ochotę na herbatę", 1, "polish");
        addSentence("I feel like having some tea", 1, "english");
        addSentence("Lubię zieloną herbatę", 1, "polish");
        addSentence("I like green tea", 1, "english");
    }


}
