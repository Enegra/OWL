package com.app.agnie.owl.Util;

import java.util.ArrayList;

/**
 * Created by agnie on 4/18/2017.
 */

public class SingletonSession {

    private static SingletonSession instance;
    private ArrayList<DictionaryEntry> dictionaryData;
    private Test selectedTest;

    private SingletonSession() {}

    public static SingletonSession Instance()
    {
        //if no instance is initialized yet then create new instance
        //else return stored instance
        if (instance == null)
        {
            instance = new SingletonSession();
        }
        return instance;
    }

    public ArrayList<DictionaryEntry> getDictionaryData(){
        return dictionaryData;
    }

    public void setDictionaryData(ArrayList<DictionaryEntry> dictionaryData){
        this.dictionaryData = dictionaryData;
    }

    public Test getSelectedTest(){
        return selectedTest;
    }

    public void setSelectedTest(Test test){
        selectedTest = test;
    }

}
