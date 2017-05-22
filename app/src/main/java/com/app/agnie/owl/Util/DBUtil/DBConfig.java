package com.app.agnie.owl.Util.DBUtil;

public class DBConfig {
    //Address of our scripts of the CRUD
    public static final String URL_GET_LANGUAGE = "http://stasis.eu/Android/db/dbGetLanguage.php";
    public static final String URL_GET_SENTENCE = "http://stasis.eu/Android/db/dbGetSentence.php";
    public static final String URL_GET_WORD = "http://stasis.eu/Android/db/dbGetWord.php";
    public static final String URL_GET_WORDDESCRIPTION = "http://stasis.eu/Android/db/dbGetWordDescription.php";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_LANGUAGE_NAME = "language_name";

    public static final String TAG_SENTENCE_ID = "id";
    public static final String TAG_SENTECE_LANGUAGENAME = "language_name";
    public static final String TAG_SENTENCE_SENTENCE = "sentence";
    public static final String TAG_SENTENCE_WORDID = "word_id";

    public static final String TAG_WORD_ID = "id";
    public static final String TAG_WORD_PICTURECONTENT = "picture_content";

    public static final String TAG_WORDDES_ID = "id";
    public static final String TAG_WORDDES_LANGUAGENAME = "language_name";
    public static final String TAG_WORDDES_WORDDESCRIPTION = "word_description";
    public static final String TAG_WORDDES_WORDID = "word_id";

    public static final String URL_GET_LESSON = "http://stasis.eu/Android/db/dbGetLesson.php";
    public static final String TAG_LESSON_CAPTION = "caption";
    public static final String TAG_LESSON_CATEGORY = "category";
    public static final String TAG_LESSON_CONTENT = "content";
    public static final String TAG_LESSON_MODIFICATIONDATE = "modification_date";
    public static final String TAG_LESSON_ORIGINLANGUAGE = "origin_language";
    public static final String TAG_LESSON_SUBTITLE = "subtitle";
    public static final String TAG_LESSON_TRANSLATIONLANGUAGE = "translation_language";
}
