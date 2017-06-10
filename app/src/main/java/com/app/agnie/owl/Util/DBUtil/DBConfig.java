package com.app.agnie.owl.Util.DBUtil;

public class DBConfig {
    //Address of our scripts of the CRUD
    public static final String URL_GET_LANGUAGE = "http://stasis.eu/Android/db/dbGetLanguage.php";
    public static final String URL_GET_SENTENCE = "http://stasis.eu/Android/db/dbGetSentence.php";
    public static final String URL_GET_WORD = "http://stasis.eu/Android/db/dbGetWord.php";
    public static final String URL_GET_WORDDESCRIPTION = "http://stasis.eu/Android/db/dbGetWordDescription.php";
    public static final String URL_GET_LESSON = "http://stasis.eu/Android/db/dbGetLesson.php";
    public static final String URL_GET_TEST = "http://stasis.eu/Android/db/dbGetTest.php";
    public static final String URL_GET_QUESTION = "http://stasis.eu/Android/db/dbGetQuestion.php";
    public static final String URL_GET_ANSWER = "http://stasis.eu/Android/db/dbGetAnswer.php";
    public static final String TAG_MODIFICATION_DATE = "modification_date";

    //JSON Tags

    public static final String TAG_ID = "id";

    public static final String TAG_JSON_ARRAY="result";

    public static final String TAG_LANGUAGE_NAME = "language_name";

    public static final String TAG_SENTECE_LANGUAGENAME = "language_name";
    public static final String TAG_SENTENCE_SENTENCE = "sentence";
    public static final String TAG_SENTENCE_WORDID = "word_id";

    public static final String TAG_WORD_PICTURECONTENT = "picture_content";

    public static final String TAG_WORDDES_LANGUAGENAME = "language_name";
    public static final String TAG_WORDDES_WORDDESCRIPTION = "word_description";
    public static final String TAG_WORDDES_WORDID = "word_id";

    public static final String TAG_LESSON_CAPTION = "caption";
    public static final String TAG_LESSON_CATEGORY = "category";
    public static final String TAG_LESSON_CONTENT = "content";
    public static final String TAG_LESSON_MODIFICATIONDATE = "modification_date";
    public static final String TAG_LESSON_ORIGINLANGUAGE = "origin_language";
    public static final String TAG_LESSON_SUBTITLE = "subtitle";
    public static final String TAG_LESSON_TRANSLATIONLANGUAGE = "translation_language";

    public static final String TAG_TEST_CAPTION = "caption";
    public static final String TAG_TEST_DESCRIPTION = "description";
    public static final String TAG_TEST_LANGUAGENAME = "language_name";
    public static final String TAG_TEST_TEXTCONTENT = "text_content";

    public static final String TAG_QUESTION_CONTENT = "content";
    public static final String TAG_QUESTION_TEST = "test";

    public static final String TAG_ANSWER_CONTENT = "content";
    public static final String TAG_ANSWER_ISCORRECT = "is_correct";
    public static final String TAG_ANSWER_QUESTION = "question";

    public static final String URL_GET_LANGUAGEDATE = "http://stasis.eu/Android/db/dbGetLanguageDate.php";
    public static final String URL_GET_SENTENCEDATE = "http://stasis.eu/Android/db/dbGetSentenceDate.php";
    public static final String URL_GET_WORDDATE = "http://stasis.eu/Android/db/dbGetWordDate.php";
    public static final String URL_GET_WORDDESCRIPTIONDATE = "http://stasis.eu/Android/db/dbGetWordDescriptionDate.php";
    public static final String URL_GET_LESSONDATE = "http://stasis.eu/Android/db/dbGetLessonDate.php";
    public static final String URL_GET_TESTDATE = "http://stasis.eu/Android/db/dbGetTestDate.php";
    public static final String URL_GET_QUESTIONDATE = "http://stasis.eu/Android/db/dbGetQuestionDate.php";
    public static final String URL_GET_ANSWERDATE = "http://stasis.eu/Android/db/dbGetAnswerDate.php";
}
