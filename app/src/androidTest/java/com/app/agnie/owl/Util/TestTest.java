package com.app.agnie.owl.Util;

import android.os.Parcel;

import com.app.agnie.owl.Util.Entities.Test;

import org.junit.Before;

import static junit.framework.Assert.assertEquals;

public class TestTest {

    private Test test;
    private Test testFromParcel;

    @Before
    public void beforeTest() {
        test = new Test("English", "Lorem", "Ipsum", "Dolor");
        Parcel mockedParcel = Parcel.obtain();
        test.writeToParcel(mockedParcel, test.describeContents());
        mockedParcel.setDataPosition(0);
        testFromParcel = Test.CREATOR.createFromParcel(mockedParcel);
    }

    @org.junit.Test
    public void writeToParcel() throws Exception {
        checkLanguage(test, testFromParcel);
        checkCaption(test, testFromParcel);
        checkDescription(test, testFromParcel);
        checkContent(test, testFromParcel);
    }

    private void checkLanguage(Test test, Test test2) {
        assertEquals(test.getLanguage(), test2.getLanguage());
    }

    private void checkCaption(Test test, Test test2) {
        assertEquals(test.getCaption(), test2.getCaption());
    }

    private void checkDescription(Test test, Test test2) {
        assertEquals(test.getDescription(), test2.getDescription());
    }

    private void checkContent(Test test, Test test2) {
        assertEquals(test.getTextContent(), test2.getTextContent());
    }

}