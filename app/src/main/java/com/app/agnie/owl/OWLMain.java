package com.app.agnie.owl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.app.agnie.owl.Util.SingletonSession;

public class OWLMain extends AppCompatActivity {

    String[] interfaceStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owlmain);
        setupInterfaceTitles();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupLanguage(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String interfaceLanguage = preferences.getString("interface_language_preference", "english");
        String learningLanguage = preferences.getString("language_preference", "german");
        SingletonSession.Instance().setInterfaceLanguage(interfaceLanguage);
        SingletonSession.Instance().setLearningLanguage(learningLanguage);
        int[] interfaceResources = new int[10];
        if (interfaceLanguage.equals("english")){
            interfaceResources[0] = R.array.main_english_interface;
            interfaceResources[1] = R.array.dictionary_english_interface;
            interfaceResources[2] = R.array.favourites_english_interface;
            interfaceResources[3] = R.array.lessons_english_interface;
            interfaceResources[4] = R.array.tests_english_interface;
        } else if (interfaceLanguage.equals("polish")){
            interfaceResources[0] = R.array.main_polish_interface;
            interfaceResources[1] = R.array.dictionary_polish_interface;
            interfaceResources[2] = R.array.favourites_polish_interface;
            interfaceResources[3] = R.array.lessons_polish_interface;
            interfaceResources[4] = R.array.tests_polish_interface;
        }
        SingletonSession.Instance().setInterfaceResources(interfaceResources);
    }

    private void setupInterfaceTitles(){
        if (SingletonSession.Instance().getInterfaceResources()==null){
            setupLanguage();
        }
        interfaceStrings = getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[0]);
        Button dictionaryButton = (Button)findViewById(R.id.main_dictionary_button);
        dictionaryButton.setText(interfaceStrings[0]);
        Button lessonButton = (Button)findViewById(R.id.main_lessons_button);
        lessonButton.setText(interfaceStrings[1]);
        Button testsButton = (Button)findViewById(R.id.main_tests_button);
        testsButton.setText(interfaceStrings[2]);
        Button favouritesButton = (Button)findViewById(R.id.main_favourites_button);
        favouritesButton.setText(interfaceStrings[3]);
    }

    public void enterDictionary(View view) {
        Intent intent = new Intent(this, Dictionary.class);
        startActivity(intent);
    }

    public void enterFavourites(View view) {
        Intent intent = new Intent(this, Favourites.class);
        startActivity(intent);
    }

    public void enterLessons(View view) {
        Intent intent = new Intent(this, Lessons.class);
        startActivity(intent);
    }

    public void enterTests(View view){
        Intent intent = new Intent(this, Tests.class);
        startActivity(intent);
    }

//    public void enterSettings(View view){
//        Intent intent = new Intent(this, Settings.class);
//        startActivity(intent);
//    }

}
