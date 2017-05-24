package com.app.agnie.owl.Util.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.app.agnie.owl.Util.Answer;
import com.app.agnie.owl.Util.CompressionTools;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.Lesson;
import com.app.agnie.owl.Util.Question;
import com.app.agnie.owl.Util.Test;

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

    void addTest(String language, String caption, String description, String textContent){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LANGUAGE, language);
        values.put(DatabaseHelper.COLUMN_CAPTION, caption);
        values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
        values.put(DatabaseHelper.COLUMN_TEXT_CONTENT, textContent);
        database.insert(DatabaseHelper.TABLE_TEST, null, values);
    }

    void addQuestion(String content, int testId){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT, content);
        values.put(DatabaseHelper.COLUMN_TEST, testId);
        database.insert(DatabaseHelper.TABLE_QUESTION, null, values);
    }

    void addAnswer(String content, int isCorrect, int questionId){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT, content);
        values.put(DatabaseHelper.COLUMN_CORRECT, isCorrect);
        values.put(DatabaseHelper.COLUMN_QUESTION, questionId);
        database.insert(DatabaseHelper.TABLE_ANSWER, null, values);
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

    public void createInitialDictionaryValues(Context context, ArrayList<String> databaseString) {
            String jsonLanguageString = databaseString.get(0);
            String jsonWordString = databaseString.get(1);
            String jsonDescriptionString = databaseString.get(2);
            String jsonSentenceString = databaseString.get(3);

            getAndAddLanguages(jsonLanguageString);
            getAndAddWords(jsonWordString);
            getAndAddWordDescriptions(jsonDescriptionString);
            getAndAddSentences(jsonSentenceString);
    }

    public void createInitialLessonValues(Context context, String databaseString) {
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("lessons_fetched", false)) {
            getAndAddLessons(databaseString);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("lessons_fetched", true);
            editor.apply();
        }
    }


    public void createInitialTestValues(Context context, String databaseString){
        SharedPreferences preferences = context.getSharedPreferences("OWLData", 0);
        if (!preferences.getBoolean("tests_fetched", false)) {
            addTest("german", "Leseverstehen", "A test for reading with comprehension, basic level", "Seit zwei Jahren wohne ich in einer Wohngemeinschaft. Wir sind vier zusammen – Sandra, Torsten, Markus und ich. Die Jungs studieren an der Technischen Universität. Ich bin Medizinstudentin, und Sandra ist  Journalistin bei einer Stadtzeitung. \n" +
                    "Die Wohnung ist nicht schlecht, sie liegt zentral und doch ruhig, hinter dem Haus ist ein Park, Wir haben vier Zimmer im dritten Stock. Die Miete ist ziemlich hoch, deshalb wohnen wir ja zu viert. Dafür haben wir eine große Küche mit einem Fenster, ein richtiges Bad und sogar eine Gästetoilette. Das Haus hat zwar eine Tiefgarage, die muss man aber extra bezahlen. Sonst aber finde ich meine Wohnung ganz toll. Es ist immer jemand und ich muss auch nicht die ganze Hausarbeit allein machen. Wir räumen jeden Freitag am Abend auf, mal die Männer, mal ich und Sandra.\n");
            addQuestion("Wo wohnt die Autorin?",1);
            addAnswer("in einer Wohngemeinschaft", 1, 1);
            addAnswer("im Wohnheim", 0, 1);
            addAnswer("im Einfamilienhaus", 0, 1);
            addAnswer("im Einzelzimmer", 0, 1);
            addQuestion("Was studiert sie?", 1);
            addAnswer("Wirtschaftskunde", 0, 2);
            addAnswer("Medizin", 1, 2);
            addAnswer("Informatik", 0, 2);
            addAnswer("Architektur", 0, 2);
            addQuestion("Wohnen in der Wohngemeinschaft nur Studenten?", 1);
            addAnswer("Ja", 1, 3);
            addAnswer("Nein", 0, 3);
            addQuestion("Wie viele Räume gibt es in der Wohnung?", 1);
            addAnswer("Sieben", 0, 4);
            addAnswer("Vier", 1, 4);
            addQuestion("Was findet die Autorin positiv?", 1);
            addAnswer("Die Wohnung ist super", 1, 5);
            addAnswer("Die Wohnung ist billig", 0, 5);
            addQuestion("Hat die Wohnung keine nachteile?", 1);
            addAnswer("Nein, alles ist OK", 0, 6);
            addAnswer("Doch, die Garage ist kostenpflichtig", 1, 6);
            addTest("german", "Leseverstehen 2", "Another reading test", "Dummy");
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("tests_fetched", true);
            editor.apply();
        }
        //// TODO: 5/21/2017 
    }

    private void getAndAddLanguages(String jsonLanguageString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonLanguageString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject languageEntity = result.getJSONObject(i);
                String languageName = languageEntity.getString(DBConfig.TAG_LANGUAGE_NAME);
                addLanguage(languageName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getAndAddWords(String jsonWordString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonWordString);
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


    private void getAndAddSentences(String jsonSentenceString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonSentenceString);
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

    private void getAndAddWordDescriptions(String jsonDescriptionString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonDescriptionString);
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

    private void getAndAddLessons(String jsonLessonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonLessonString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String caption = jo.getString(DBConfig.TAG_LESSON_CAPTION);
                //     String category = jo.getString(DBConfig.TAG_LESSON_CATEGORY);
                String content = jo.getString(DBConfig.TAG_LESSON_CONTENT);
                String originLanguage = jo.getString(DBConfig.TAG_LESSON_ORIGINLANGUAGE);
                String subtitle = jo.getString(DBConfig.TAG_LESSON_SUBTITLE);
                String translationLanguage = jo.getString(DBConfig.TAG_LESSON_TRANSLATIONLANGUAGE);
                addLesson(caption, subtitle, content, originLanguage, translationLanguage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DictionaryEntry> getDictionaryEntries(String language, String interfaceLanguage) {
        ArrayList<DictionaryEntry> dictionaryEntries = new ArrayList<>();
//        Cursor cursor = database.query(DatabaseHelper.TABLE_WORD, null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("select word_id, picture_content, word_description, language_name from word join word_description on word.id=word_description.word_id order by word_id asc", null);
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
        Cursor cursor = database.rawQuery("select id, caption, subtitle, content, origin_language, translation_language from lesson", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String originLanguage = cursor.getString(cursor.getColumnIndex("origin_language"));
            String translationLanguage = cursor.getString(cursor.getColumnIndex("translation_language"));
            if (originLanguage.equals(language) && translationLanguage.equals(interfaceLanguage)) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String caption = cursor.getString(cursor.getColumnIndex("caption"));
                String subtitle = cursor.getString(cursor.getColumnIndex("subtitle"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                Lesson newLesson = new Lesson(id, originLanguage, translationLanguage, content, caption, subtitle);
                lessons.add(newLesson);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return lessons;
    }

    public ArrayList<Test> getTests(String language){
        ArrayList<Test> tests = new ArrayList<>();
        Cursor cursor = database.rawQuery("select question.test as test_id, question.id as question_id, question.content as question_content, test.caption as test_caption, test.description as test_description, test.text_content as test_text_content, test.language_name as test_language, answer.content as answer_content, answer.is_correct as answer_is_correct from (question join test on question.test = test.id) join answer on answer.question = question.id order by test_id, question_id asc", null);
        cursor.moveToFirst();
        int lastId = -1;
        int lastQuestionId = -1;
        while (!cursor.isAfterLast()){
            String testLanguage = cursor.getString(cursor.getColumnIndex("test_language"));
            if (testLanguage.equals(language)){
                int testId = cursor.getInt(cursor.getColumnIndex("test_id"));
                if (testId!=lastId){
                    String caption = cursor.getString(cursor.getColumnIndex("test_caption"));
                    String description = cursor.getString(cursor.getColumnIndex("test_description"));
                    String textContent = cursor.getString(cursor.getColumnIndex("test_text_content"));
                    Test newTest = new Test(testLanguage, caption, description, textContent);
                    tests.add(newTest);
                    lastId = testId;
                }
                int questionId = cursor.getInt(cursor.getColumnIndex("question_id"));
                if (questionId!=lastQuestionId){
                    String content = cursor.getString(cursor.getColumnIndex("question_content"));
                    Question newQuestion = new Question(content);
                    tests.get(tests.size()-1).addQuestion(newQuestion);
                    lastQuestionId = questionId;
                }
                String answerContent = cursor.getString(cursor.getColumnIndex("answer_content"));
                int answerIsCorrect = cursor.getInt(cursor.getColumnIndex("answer_is_correct"));
                boolean isCorrect;
                if (answerIsCorrect==0){
                    isCorrect = false;
                } else {
                    isCorrect = true;
                }
                Answer newAnswer = new Answer(answerContent, isCorrect);
                tests.get(tests.size()-1).getQuestions().get(tests.get(tests.size()-1).getQuestions().size()-1).addAnswer(newAnswer);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return tests;
    }

}
