package com.app.agnie.owl.Util;

import com.app.agnie.owl.Util.Entities.DictionaryEntry;
import com.app.agnie.owl.Util.Entities.Test;

import java.util.ArrayList;

/**
 * Created by agnie on 4/18/2017.
 */

public class SingletonSession {

    private static SingletonSession instance;
    private ArrayList<DictionaryEntry> dictionaryData;
    private Test selectedTest;
    private DictionaryEntry selectedEntry;
    private int[] interfaceResources;
    private String interfaceLanguage;
    private String learningLanguage;

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

    public DictionaryEntry getSelectedEntry(){
        return selectedEntry;
    }

    public void setSelectedEntry(DictionaryEntry selectedEntry){
        this.selectedEntry = selectedEntry;
    }

    public void setInterfaceResources(int[] interfaceResources){
        this.interfaceResources = interfaceResources;
    }

    public int[] getInterfaceResources(){
        return interfaceResources;
    }

    public String getLearningLanguage() {
        return learningLanguage;
    }

    public void setLearningLanguage(String learningLanguage) {
        this.learningLanguage = learningLanguage;
    }

    public String getInterfaceLanguage() {
        return interfaceLanguage;
    }

    public void setInterfaceLanguage(String interfaceLanguage) {
        this.interfaceLanguage = interfaceLanguage;
    }

}
