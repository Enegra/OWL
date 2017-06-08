package com.app.agnie.owl.Util.DBUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DatabaseHelper extends SQLiteOpenHelper {

    static final String TABLE_CATEGORY = "category";
    static final String COLUMN_CATEGORY = "category";

    static final String TABLE_WORD = "word";
    static final String COLUMN_ID = "id";
    static final String COLUMN_PICTURE_CONTENT = "picture_content";

    static final String TABLE_LANGUAGE = "language";
    static final String COLUMN_LANGUAGE = "language_name";

    static final String TABLE_WORD_DESCRIPTION = "word_description";
    static final String COLUMN_WORD_DESCRIPTION = "word_description";
    static final String COLUMN_WORD_ID = "word_id";
    static final String COLUMN_SOUND = "sound";

    static final String TABLE_SENTENCE = "sentence";
    static final String COLUMN_SENTENCE = "sentence";

    static final String TABLE_LESSON = "lesson";
    static final String COLUMN_CAPTION = "caption";
    static final String COLUMN_SUBTITLE = "subtitle";
    static final String COLUMN_ORIGIN_LANGUAGE = "origin_language";
    static final String COLUMN_TRANSLATION_LANGUAGE = "translation_language";
    static final String COLUMN_CONTENT = "content";

    static final String TABLE_TEST = "test";
    static final String COLUMN_DESCRIPTION = "description";
    static final String COLUMN_TEXT_CONTENT = "text_content";

    static final String TABLE_QUESTION = "question";
    static final String COLUMN_TEST = "test";

    static final String TABLE_ANSWER = "answer";
    static final String COLUMN_CORRECT = "is_correct";
    static final String COLUMN_QUESTION = "question";

    private static final String DATABASE_NAME = "owl_content.db";
    private static final int DATABASE_VERSION = 1;


    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        createAnswer(database);
        createQuestion(database);
        createCategory(database);
        createWord(database);
        createLanguage(database);
        createWordDescription(database);
        createSentence(database);
        createLesson(database);
        createTest(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        dropAnswer(database);
        dropQuestion(database);
        dropTest(database);
        dropLesson(database);
        dropSentence(database);
        dropWordDescription(database);
        dropLanguage(database);
        dropWord(database);
        dropCategory(database);
        onCreate(database);
    }

    private void createCategory(SQLiteDatabase database) {
        String createCategory = "create table if not exists " + TABLE_CATEGORY + "(" + COLUMN_CATEGORY + " integer primary key);";
        database.execSQL(createCategory);
    }

    private void dropCategory(SQLiteDatabase database) {
        database.execSQL("drop table if exists " + TABLE_CATEGORY);
    }

    private void createWord(SQLiteDatabase database) {
        String createWord = "create table if not exists " + TABLE_WORD + "(" + COLUMN_ID + " integer primary key,  " + COLUMN_PICTURE_CONTENT + " blob" + ");";
        database.execSQL(createWord);
    }

    private void dropWord(SQLiteDatabase database) {
        database.execSQL("drop table if exists " + TABLE_WORD);
    }

    private void createLanguage(SQLiteDatabase database) {
        String createLanguage = "create table if not exists " + TABLE_LANGUAGE + "(" + COLUMN_LANGUAGE + " text primary key);";
        database.execSQL(createLanguage);
    }

    private void dropLanguage(SQLiteDatabase database) {
        database.execSQL("drop table if exists " + TABLE_LANGUAGE);
    }

    private void createWordDescription(SQLiteDatabase database) {
        String createWordDescription = "create table if not exists " + TABLE_WORD_DESCRIPTION + "(" + COLUMN_ID + " integer primary key, " + COLUMN_WORD_DESCRIPTION + " text not null, " + COLUMN_SOUND + " text, " + COLUMN_WORD_ID + " integer not null, " + COLUMN_LANGUAGE + " text not null, foreign key (" + COLUMN_WORD_ID + ") references " + TABLE_WORD + "(" + COLUMN_ID + "), foreign key (" + COLUMN_LANGUAGE + ") references " + TABLE_LANGUAGE + "( " + COLUMN_LANGUAGE + ") );";
        database.execSQL(createWordDescription);
    }

    private void dropWordDescription(SQLiteDatabase database) {
        database.execSQL("drop table if exists " + TABLE_WORD_DESCRIPTION);
    }

    private void createSentence(SQLiteDatabase database) {
        String createSentence = "create table if not exists " + TABLE_SENTENCE + "(" + COLUMN_ID + " integer primary key, " + COLUMN_SENTENCE + " text not null, " + COLUMN_SOUND + " text, "+ COLUMN_WORD_ID + " integer not null, " + COLUMN_LANGUAGE + " text not null, " + "foreign key (" + COLUMN_WORD_ID + ") references " + TABLE_WORD + "(" + COLUMN_ID + "), foreign key (" + COLUMN_LANGUAGE + ") references " + TABLE_LANGUAGE + "( " + COLUMN_LANGUAGE + ") );";
        database.execSQL(createSentence);
    }

    private void dropSentence(SQLiteDatabase database) {
        database.execSQL("drop table if exists " + TABLE_SENTENCE);
    }

    private void createLesson(SQLiteDatabase database){
        String createLesson = "create table if not exists " + TABLE_LESSON + "(" + COLUMN_ID + " integer primary key, " + COLUMN_CATEGORY + " integer, " + COLUMN_CAPTION + " text not null, " + COLUMN_SUBTITLE + " text not null, " + COLUMN_CONTENT + " text not null, " + COLUMN_ORIGIN_LANGUAGE + " text not null, " + COLUMN_TRANSLATION_LANGUAGE + " text not null, " + "foreign key (" + COLUMN_ORIGIN_LANGUAGE + ") references " + TABLE_LANGUAGE + "( " + COLUMN_LANGUAGE + "), foreign key (" + COLUMN_TRANSLATION_LANGUAGE + ") references " + TABLE_LANGUAGE + "( " + COLUMN_LANGUAGE + ")) ";
        database.execSQL(createLesson);
    }

    private void dropLesson(SQLiteDatabase database){
        database.execSQL("drop table if exists " + TABLE_LESSON);
    }

    private void createTest(SQLiteDatabase database){
        String createTest = "create table if not exists " + TABLE_TEST + "(" + COLUMN_ID + " integer primary key, " + COLUMN_LANGUAGE + " text not null, " + COLUMN_CAPTION + " text not null, " + COLUMN_DESCRIPTION + " text not null, " + COLUMN_TEXT_CONTENT + " text not null, foreign key (" + COLUMN_LANGUAGE +") references " + TABLE_LANGUAGE + "( " + COLUMN_LANGUAGE + "));";
        database.execSQL(createTest);
    }

    private void dropTest(SQLiteDatabase database){
        database.execSQL("drop table if exists " + TABLE_TEST);
    }

    private void createQuestion(SQLiteDatabase database){
        String createQuestion = "create table if not exists " + TABLE_QUESTION + "(" + COLUMN_ID + " integer primary key, " + COLUMN_CONTENT + " text not null, " + COLUMN_TEST + " integer not null, foreign key (" + COLUMN_TEST + ") references " + TABLE_TEST + "(" + COLUMN_ID + "));";
        database.execSQL(createQuestion);
    }

    private void dropQuestion(SQLiteDatabase database){
        database.execSQL("drop table if exists " + TABLE_QUESTION);
    }

    private void createAnswer(SQLiteDatabase database){
        String createAnswer = "create table if not exists " + TABLE_ANSWER + "(" + COLUMN_ID + " integer primary key, " + COLUMN_CONTENT + " text not null, " + COLUMN_CORRECT + " integer not null, " + COLUMN_QUESTION + " integer not null, foreign key (" + COLUMN_QUESTION + ") references " + TABLE_QUESTION + "(" + COLUMN_ID + "));";
        database.execSQL(createAnswer);
    }

    private void dropAnswer(SQLiteDatabase database){
        database.execSQL("drop table if exists " + TABLE_ANSWER);
    }

}
