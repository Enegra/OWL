package com.app.agnie.owl.Util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DictionaryDataSource {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public DictionaryDataSource(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
    }

    public void addCategory(int category) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CATEGORY, category);
        database.insert(DatabaseHelper.TABLE_CATEGORY, null, values);
    }

    void addWord(String wordPicture) {
        ContentValues values = new ContentValues();
        byte[] pictureContent = retrievePictureContent(wordPicture);
        values.put(DatabaseHelper.COLUMN_PICTURE, wordPicture);
        values.put(DatabaseHelper.COLUMN_PICTURE_CONTENT, pictureContent);
        database.insert(DatabaseHelper.TABLE_WORD, null, values);
    }

    void addLanguage(String languageName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LANGUAGE, languageName);
        database.insert(DatabaseHelper.TABLE_LANGUAGE, null, values);
    }

    void addWordDescription(String description, int wordID, String language) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_WORD_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_WORD_ID, wordID);
        values.put(DatabaseHelper.COLUMN_LANGUAGE, language);
        database.insert(DatabaseHelper.TABLE_WORD_DESCRIPTION, null, values);
    }

    void addSentence(String sentence, int wordID, String language) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_SENTENCE, sentence);
        values.put(DatabaseHelper.COLUMN_WORD_ID, wordID);
        values.put(DatabaseHelper.COLUMN_LANGUAGE, language);
        database.insert(DatabaseHelper.TABLE_SENTENCE, null, values);
    }

    private byte[] retrievePictureContent(String pictureName) {
        try {
            URL imageUrl = new URL("http://users.jyu.fi/~anvalton/pollo/" + pictureName);
            URLConnection connection = imageUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int current = 0;
            while ((current = bufferedInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, current);
            }
            return buffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createInitialValues(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("database_fetched", false)) {
            addWord("mug.png");
            addLanguage("polish");
            addLanguage("english");
            addLanguage("german");
            addWordDescription("kubek", 1, "polish");
            addWordDescription("mug", 1, "english");
            addSentence("Ten kubek jest brudny", 1, "polish");
            addSentence("Mój ulubiony kubek jest niebieski", 1, "polish");
            addSentence("Zbiłeś mój kubek!", 1, "polish");
            addSentence("This mug is dirty", 1, "english");
            addSentence("My favourite mug is blue", 1, "english");
            addSentence("You broke my mug!", 1, "english");
            addWord("tea.png");
            addWordDescription("herbata", 2, "polish");
            addWordDescription("tea", 2, "english");
            addSentence("Mam ochotę na herbatę", 2, "polish");
            addSentence("I feel like having some tea", 2, "english");
            addSentence("Lubię zieloną herbatę", 2, "polish");
            addSentence("I like green tea", 2, "english");
            addWord("snowdrops.png");
            addWordDescription("przebiśnieg", 3, "polish");
            addWordDescription("snowdrop", 3, "english");
            addSentence("Przebiśniegi są jednym z pierwszych znaków wiosny", 3, "polish");
            addSentence("Snowdrops are one of the first signs of spring", 3, "english");
            addSentence("Mam przebiśniegi w ogrodzie", 3, "polish");
            addSentence("I have snowdrops in my garden", 3, "english");
            addWord("coffee.png");
            addWordDescription("kawa", 4, "polish");
            addWordDescription("coffee", 4, "english");
            addSentence("Mam ochotę na kawę", 4, "polish");
            addSentence("I feel like having some coffee", 4, "english");
            addSentence("Lubię zapach świeżo mielonej kawy", 4, "polish");
            addSentence("I like the smell of freshly ground coffee", 4, "english");
            addSentence("Czy możesz mi zrobić kawę?", 4, "polish");
            addSentence("Could make me coffee?", 4, "english");
            addWord("dog.png");
            addWordDescription("pies", 5, "polish");
            addWordDescription("dog", 5, "english");
            addSentence("Lubię psy", 5, "polish");
            addSentence("I like dogs", 5, "english");
            addSentence("Chciałbym mieć psa", 5, "polish");
            addSentence("I would like to have a dog", 5, "english");
            addWord("fries.png");
            addWordDescription("frytki", 6, "polish");
            addWordDescription("french fries", 6, "english");
            addSentence("A może frytki do tego", 6, "polish");
            addSentence("Would you like fries with that?", 6, "english");
            addSentence("Na obiad będzie kotlet schabowy z frytkami", 6, "polish");
            addSentence("For the dinner there will be a pork chop with fries", 6, "english");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("database_fetched", true);
            editor.apply();
        }
    }

    public ArrayList<DictionaryEntry> getDictionaryEntries(String language, String interfaceLanguage) {
        ArrayList<DictionaryEntry> dictionaryEntries = new ArrayList<>();
//        Cursor cursor = database.query(DatabaseHelper.TABLE_WORD, null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("select word_id, picture_name, picture_content, word_description, language_name from word join word_description on word.id=word_description.word_id", null);
        Cursor sentenceCursor = database.rawQuery("select * from sentence order by word_id, id asc", null);
        sentenceCursor.moveToFirst();
        cursor.moveToFirst();
        int last = -1;
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("word_id"));
            if (id != last) {
                String picture = cursor.getString(cursor.getColumnIndex("picture_name"));
                byte[] pictureContent = cursor.getBlob(cursor.getColumnIndex("picture_content"));
                DictionaryEntry newEntry = new DictionaryEntry(id, picture, pictureContent);
                dictionaryEntries.add(newEntry);
            }
            String wordLanguage = cursor.getString(cursor.getColumnIndex("language_name"));
            if (wordLanguage.equals(language)) {
                String caption = cursor.getString(cursor.getColumnIndex("word_description"));
                dictionaryEntries.get(dictionaryEntries.size() - 1).setCaption(caption);
            } else if (wordLanguage.equals(interfaceLanguage)) {
                String captionTranslation = cursor.getString(cursor.getColumnIndex("word_description"));
                dictionaryEntries.get(dictionaryEntries.size() - 1).setCaptionTranslation(captionTranslation);
            }
            if (!sentenceCursor.isAfterLast()) {
                int wordId = sentenceCursor.getInt(sentenceCursor.getColumnIndex("word_id"));
                while (id == wordId) {
                    String sentenceLanguage = sentenceCursor.getString(sentenceCursor.getColumnIndex("language_name"));
                    if (sentenceLanguage.equals(language)) {
                        String sentence = sentenceCursor.getString(sentenceCursor.getColumnIndex("sentence"));
                        dictionaryEntries.get(dictionaryEntries.size() - 1).setSentence(sentence);
                    } else if (sentenceLanguage.equals(interfaceLanguage)) {
                        String sentence = sentenceCursor.getString(sentenceCursor.getColumnIndex("sentence"));
                        dictionaryEntries.get(dictionaryEntries.size() - 1).setSentenceTranslation(sentence);
                    }
                    sentenceCursor.moveToNext();
                    if (sentenceCursor.isAfterLast()) {
                        break;
                    }
                    wordId = sentenceCursor.getInt(sentenceCursor.getColumnIndex("word_id"));
                }
            }


            last = id;
            cursor.moveToNext();
        }
        cursor.close();
        sentenceCursor.close();
        return dictionaryEntries;
    }

}
