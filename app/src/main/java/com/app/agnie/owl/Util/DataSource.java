package com.app.agnie.owl.Util;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public void createInitialDictionaryValues(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("dictionary_fetched", false)) {
            addWord("mug.png");
            addLanguage("polish");
            addLanguage("english");
            addLanguage("german");
            addWordDescription("kubek", 1, "polish");
            addWordDescription("mug", 1, "english");
            addWordDescription("der Becher", 1, "german");
            addSentence("Ten kubek jest brudny", 1, "polish");
            addSentence("Mój ulubiony kubek jest niebieski", 1, "polish");
            addSentence("Zbiłeś mój kubek!", 1, "polish");
            addSentence("This mug is dirty", 1, "english");
            addSentence("My favourite mug is blue", 1, "english");
            addSentence("You broke my mug!", 1, "english");
            addSentence("Dieser Becher ist schmutzig", 1, "german");
            addSentence("Mein Lieblingsbecher ist blau", 1, "german");
            addSentence("Du hast meinen Becher gebrochen!", 1, "german");
            addWord("tea.png");
            addWordDescription("herbata", 2, "polish");
            addWordDescription("tea", 2, "english");
            addWordDescription("der Tee", 2, "german");
            addSentence("Mam ochotę na herbatę", 2, "polish");
            addSentence("I feel like having some tea", 2, "english");
            addSentence("Ich habe Lust um Tee zu trinken", 2, "german");
            addSentence("Lubię zieloną herbatę", 2, "polish");
            addSentence("I like green tea", 2, "english");
            addSentence("Ich mag grüner Tee", 2, "german");
            addWord("snowdrops.png");
            addWordDescription("przebiśnieg", 3, "polish");
            addWordDescription("snowdrop", 3, "english");
            addWordDescription("das Schneeglöckchen", 3, "german");
            addSentence("Przebiśniegi są jednym z pierwszych znaków wiosny", 3, "polish");
            addSentence("Snowdrops are one of the first signs of spring", 3, "english");
            addSentence("Schneeglöckchen sind eines der ersten Anzeichen des Frühlings", 3, "german");
            addSentence("Mam przebiśniegi w ogrodzie", 3, "polish");
            addSentence("I have snowdrops in the garden", 3, "english");
            addSentence("Ich have Schneeglöckchen im Garten", 3, "german");
            addWord("coffee.png");
            addWordDescription("kawa", 4, "polish");
            addWordDescription("coffee", 4, "english");
            addWordDescription("der Kaffee", 4, "german");
            addSentence("Mam ochotę na kawę", 4, "polish");
            addSentence("I feel like having some coffee", 4, "english");
            addSentence("Ich habe Lust um Kaffee zu trinken", 4, "german");
            addSentence("Lubię zapach świeżo mielonej kawy", 4, "polish");
            addSentence("I like the smell of freshly ground coffee", 4, "english");
            addSentence("Ich mag den Geruch von frisch gemahlenem Kaffee", 4, "german");
            addSentence("Czy mógłbyś mi zrobić kawę?", 4, "polish");
            addSentence("Could you make me coffee?", 4, "english");
            addSentence("Könntest du mich einen Kaffee machen?", 4, "german");
            addWord("dog.png");
            addWordDescription("pies", 5, "polish");
            addWordDescription("dog", 5, "english");
            addWordDescription("der Hund", 5, "german");
            addSentence("Lubię psy", 5, "polish");
            addSentence("I like dogs", 5, "english");
            addSentence("Ich mag Hunde", 5, "german");
            addSentence("Chciałbym mieć psa", 5, "polish");
            addSentence("I would like to have a dog", 5, "english");
            addSentence("Ich möchte einen Hund haben", 5, "german");
            addWord("fries.png");
            addWordDescription("frytki", 6, "polish");
            addWordDescription("french fries", 6, "english");
            addWordDescription("Pommes", 6, "german");
            addSentence("A może frytki do tego", 6, "polish");
            addSentence("Would you like fries with that?", 6, "english");
            addSentence("Möchten Sie noch Pommes damit?", 6, "german");
            addSentence("Na obiad będzie kotlet schabowy z frytkami", 6, "polish");
            addSentence("For the dinner there will be a pork chop with fries", 6, "english");
            addSentence("Das Mittagessen wird Schweinekotelett mit Pommes", 6, "german");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("dictionary_fetched", true);
            editor.apply();
        }
    }

    public void createInitialLessonValues(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("lessons_fetched", false)) {
            String lessonOneContent = " <![CDATA[\n" +
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
                    "        </html>\n" +
                    "        ]]>";
            addLesson("Die Einführung", "The introduction; basic vocabulary used when meeting a person for the first time", lessonOneContent, "german", "english");
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
