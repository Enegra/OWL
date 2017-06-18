package com.app.agnie.owl.Util;


import android.os.Parcel;

import com.app.agnie.owl.Util.Entities.Lesson;

import org.junit.Before;

import static junit.framework.Assert.assertEquals;

public class LessonTest {

    private Lesson testLesson;
    private Lesson lessonFromParcel;

    @Before
    public void beforeTest() {
        testLesson = new Lesson(1, "English", "Polish", "Ipsum", "Larum", "Dare");
        Parcel mockedParcel = Parcel.obtain();
        testLesson.writeToParcel(mockedParcel, testLesson.describeContents());
        mockedParcel.setDataPosition(0);
        lessonFromParcel = Lesson.CREATOR.createFromParcel(mockedParcel);

    }

    @org.junit.Test
    public void writeToParcel() throws Exception {
        checkId(testLesson, lessonFromParcel);
        checkLanguage(testLesson, lessonFromParcel);
        checkTranslation(testLesson, lessonFromParcel);
        checkCaption(testLesson, lessonFromParcel);
        checkSubtitle(testLesson, lessonFromParcel);
        checkContent(testLesson, lessonFromParcel);
    }

    private void checkId(Lesson lesson1, Lesson lesson2) {
        assertEquals(lesson1.getId(), lesson2.getId());
    }

    private void checkLanguage(Lesson lesson1, Lesson lesson2) {
        assertEquals(lesson1.getLanguage(), lesson2.getLanguage());
    }

    private void checkTranslation(Lesson lesson1, Lesson lesson2) {
        assertEquals(lesson1.getTranslation(), lesson2.getTranslation());
    }

    private void checkCaption(Lesson lesson1, Lesson lesson2) {
        assertEquals(lesson1.getCaption(), lesson2.getCaption());
    }

    private void checkSubtitle(Lesson lesson1, Lesson lesson2) {
        assertEquals(lesson1.getSubtitle(), lesson2.getSubtitle());
    }

    private void checkContent(Lesson lesson1, Lesson lesson2) {
        assertEquals(lesson1.getContent(), lesson2.getContent());
    }

}