package com.app.agnie.owl.Util.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.app.agnie.owl.Util.CompressionTools;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.Lesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DataSource {

    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    private String JsonLanguageString;
    private String JsonWordString;
    private String JsonDescriptionString;
    private String JsonSentenceString;


    public DataSource(Context context) {
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
        byte[] compressedPicture = CompressionTools.compress(pictureContent);
        values.put(DatabaseHelper.COLUMN_PICTURE_CONTENT, compressedPicture);
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

    void addLesson(String caption, String subtitle, String content, String originLanguage, String translationLanguage) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CAPTION, caption);
        values.put(DatabaseHelper.COLUMN_SUBTITLE, subtitle);
        values.put(DatabaseHelper.COLUMN_CONTENT, content);
        values.put(DatabaseHelper.COLUMN_ORIGIN_LANGUAGE, originLanguage);
        values.put(DatabaseHelper.COLUMN_TRANSLATION_LANGUAGE, translationLanguage);
        database.insert(DatabaseHelper.TABLE_LESSON, null, values);
    }

    private byte[] retrievePictureContent(String pictureName) {
        try {
            URL imageUrl = new URL("http://users.jyu.fi/~anvalton/pollo/" + pictureName);
            URLConnection connection = imageUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[128];
            int current;
            while ((current = bufferedInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, current);
            }
            bufferedInputStream.close();
            inputStream.close();
            buffer.close();
            return buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createInitialDictionaryValues(Context context, ArrayList<String> databasestring) {
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("dictionary_fetched", false)) {

            JsonLanguageString = databasestring.get(0);
            JsonWordString = databasestring.get(1);
            JsonDescriptionString = databasestring.get(2);
            JsonSentenceString = databasestring.get(3);

            getAndAddLanguages();
            getAndAddWords();
            getAndAddWordDescriptions();
            getAndAddSentences();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("dictionary_fetched", true);
            editor.apply();
        }
    }

    private void getAndAddLanguages() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JsonLanguageString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(DBConfig.TAG_LANGUAGE_NAME);
                addLanguage(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAndAddWords() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JsonWordString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String picture_content = jo.getString(DBConfig.TAG_WORD_PICTURECONTENT);
                addWord(picture_content);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getAndAddSentences() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JsonSentenceString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(DBConfig.TAG_SENTECE_LANGUAGENAME);
                String sentence = jo.getString(DBConfig.TAG_SENTENCE_SENTENCE);
                String word_id = jo.getString(DBConfig.TAG_SENTENCE_WORDID);
                int wId = Integer.parseInt(word_id);
                addSentence(sentence, wId, name);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAndAddWordDescriptions() {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(JsonDescriptionString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String language_name = jo.getString(DBConfig.TAG_WORDDES_LANGUAGENAME);
                String word_description = jo.getString(DBConfig.TAG_WORDDES_WORDDESCRIPTION);
                String word_id = jo.getString(DBConfig.TAG_WORDDES_WORDID);
                int wId = Integer.parseInt(word_id);
                addWordDescription(word_description, wId, language_name);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createInitialLessonValues(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("lessons_fetched", false)) {
            String lessonOneContent =
                    "        <!doctype html>\n" +
                    "        <head>\n" +
                    "        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"/>\n" +
                    "        </head>\n" +
                    "        <body>\n" +
                    "        <section><p>Hallo, ich heiße Anna, Ich komme aus Breslau und ich wohne hier. Ich studiere nicht, ich arbeite als Lektorin.</p>\n" +
                    "\t\t<p class=\"translation\">Hello, my name is Anna. I am from Wrocław and I live there. I am not studying, I am working as a lecturer.</p>\n" +
                    "\t\t<p>Wie heißen Sie?</p>\n" +
                    "\t\t<p class=\"translation\">What is your name?</p>\n" +
                    "\t\t<p>Woher kommen Sie?</p>\n" +
                    "\t\t<p class=\"translation\">Where are you from?</p>\n" +
                    "\t\t<p>Wo wohnen Sie?</p>\n" +
                    "\t\t<p class=\"translation\">Where do you live?</p>\n" +
                    "\t\t<p>Studieren Sie oder arbeiten Sie?</p>\n" +
                    "\t\t<p class=\"translation\">Are you studying or working?</p>\n" +
                    "\t\t<p>Was studieren Sie? / Wo arbeiten Sie?</p>\n" +
                    "\t\t<p class=\"translation\">What are you studying? / Where are you working?</p>\n" +
                    "\t\t<p>Ich bin verheiratet und habe 2 Kinder. Ich habe auch 2 Hunde. Von Beruf bin ich Germanistin.</p>\n" +
                    "\t\t<p class=\"translation\">I am married and have two children. I also have two dogs. I am a germanist by profession.</p>\n" +
                    "\t\t<p>Sind Sie verheiratet oder ledig?</p>\n" +
                    "\t\t<p class=\"translation\">Are you married or single?</p>\n" +
                    "\t\t<p>Haben Sie Kinder?</p>\n" +
                    "\t\t<p class=\"translation\">Do you have children?</p>\n" +
                    "\t\t<p>Haben Sie ein Haustier? Eine Katze? Einen Hund?</p>\n" +
                    "\t\t<p class=\"translation\">Do you have a pet? A cat? A dog?</p>\n" +
                    "\t\t<p>Was sind Sie von Beruf?</p>\n" +
                    "\t\t<p class=\"translation\">What is your profession?</p>\n" +
                    "\t\t<p>Meine Tochter ist Studentin. Sie heißt Agnes. Sie studiert Informatik. Sie wohnt jetzt in Finnland. Sie arbeitet noch nicht.</p>\n" +
                    "\t\t<p class=\"translation\">My daughter is a student. Her name is Agnes. She is studying computer science. She lives now in Finland. She doesn't work yet.</p>\n" +
                    "        </section></body>\n" +
                    "        </html>\n";
            String lessonTwoContent =
                    "        <!doctype html>\n" +
                    "        <head>\n" +
                    "        <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"/>\n" +
                    "        </head>\n" +
                    "        <body>\n" +
                    "        <p>Das Einfamilienhaus</p>\n" +
                    "\t\t<p class=\"translation\">Detached house</p>\n" +
                    "\t\t<p>Das Doppelhaus</p>\n" +
                    "\t\t<p class=\"translation\">Semi-detached house</p>\n" +
                    "\t\t<p>Das Reihenhaus</p>\n" +
                    "\t\t<p class=\"translation\">Terraced house</p>\n" +
                    "\t\t<p>Der Altbau</p>\n" +
                    "\t\t<p class=\"translation\">Old building</p>\n" +
                    "\t\t<p>Der Garten</p>\n" +
                    "\t\t<p class=\"translation\">Garden</p>\n" +
                    "\t\t<p>Der Hof</p>\n" +
                    "\t\t<p class=\"translation\">Yard</p>\n" +
                    "\t\t<p>Die Garage</p>\n" +
                    "\t\t<p class=\"translation\">Garage</p>\n" +
                    "\t\t<p>Der Balkon</p>\n" +
                    "\t\t<p class=\"translation\">Balcony</p>\n" +
                    "\t\t<p>Das Dach</p>\n" +
                    "\t\t<p class=\"translation\">Roof</p>\n" +
                    "\t\t<p>Die Wand</p>\n" +
                    "\t\t<p class=\"translation\">Wall</p>\n" +
                    "\t\t<p>Die Decke</p>\n" +
                    "\t\t<p class=\"translation\">Ceiling</p>\n" +
                    "\t\t<p>Der Boden</p>\n" +
                    "\t\t<p class=\"translation\">Floor</p>\n" +
                    "\t\t<p>Das Erdgeschoss</p>\n" +
                    "\t\t<p class=\"translation\">Ground floor</p>\n" +
                    "\t\t<p>Der erste Stock</p>\n" +
                    "\t\t<p class=\"translation\">First floor</p>\n" +
                    "\t\t<p>Die Treppe</p>\n" +
                    "\t\t<p class=\"translation\">Stairs</p>\n" +
                    "\t\t<p>Die Küche</p>\n" +
                    "\t\t<p class=\"translation\">Kitchen</p>\n" +
                    "\t\t<p>Der Flur</p>\n" +
                    "\t\t<p class=\"translation\">Corridor</p>\n" +
                    "\t\t<p>Das Wohnzimmer</p>\n" +
                    "\t\t<p class=\"translation\">Living Room</p>\n" +
                    "\t\t<p>Das Schlafzimmer</p>\n" +
                    "\t\t<p class=\"translation\">Sleeping Room</p>\n" +
                    "\t\t<p>Die Miete</p>\n" +
                    "\t\t<p class=\"translation\">Rent</p>\n" +
                    "\t\t<p>Eine Wohnung mieten</p>\n" +
                    "\t\t<p class=\"translation\">To rent an apartment</p>\n" +
                    "\t\t<p>Umziehen</p>\n" +
                    "\t\t<p class=\"translation\">To move</p>\n" +
                    "\t\t<p>Der Umzug</p>\n" +
                    "\t\t<p class=\"translation\">Move</p>\n" +
                    "        </body>\n" +
                    "        </html>\n";
            addLesson("Die Einführung", "The introduction; basic vocabulary used when meeting a person for the first time", lessonOneContent, "german", "english");
            addLesson("Die Wohnung", "Home. Vocabulary related to houses and apartments", lessonTwoContent, "german", "english");
//            addLesson("Powitania", "Basic lesson on greetings", "", "polish", "english");
//            addLesson("Pogoda", "Weather-related vocabulary", "", "polish", "english");
//            addLesson("Zaimki osobowe", "Personal pronouns explained", "", "polish", "english");
//            addLesson("Jedzenie", "Food-related vocabulary", "", "polish", "english");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("lessons_fetched", true);
            editor.apply();
        }
    }

    public ArrayList<DictionaryEntry> getDictionaryEntries(String language, String interfaceLanguage) {
        ArrayList<DictionaryEntry> dictionaryEntries = new ArrayList<>();
//        Cursor cursor = database.query(DatabaseHelper.TABLE_WORD, null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("select word_id, picture_content, word_description, language_name from word join word_description on word.id=word_description.word_id", null);
        Cursor sentenceCursor = database.rawQuery("select * from sentence order by word_id, id asc", null);
        sentenceCursor.moveToFirst();
        cursor.moveToFirst();
        int last = -1;
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("word_id"));
            if (id != last) {
                byte[] pictureContent = cursor.getBlob(cursor.getColumnIndex("picture_content"));
                DictionaryEntry newEntry = new DictionaryEntry(id, pictureContent);
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

    public ArrayList<Lesson> getLessons(String language, String interfaceLanguage) {
        ArrayList<Lesson> lessons = new ArrayList<>();
//        Cursor cursor = database.query(DatabaseHelper.TABLE_LESSON, null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("select id, caption, subtitle, content, origin_language, translation_language from lesson", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String caption = cursor.getString(cursor.getColumnIndex("caption"));
            String subtitle = cursor.getString(cursor.getColumnIndex("subtitle"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String originLanguage = cursor.getString(cursor.getColumnIndex("origin_language"));
            String translationLanguage = cursor.getString(cursor.getColumnIndex("translation_language"));
            Lesson newLesson = new Lesson(id, originLanguage, translationLanguage, content, caption, subtitle);
            if (originLanguage.equals(language) && translationLanguage.equals(interfaceLanguage)) {
                lessons.add(newLesson);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return lessons;
    }

}
