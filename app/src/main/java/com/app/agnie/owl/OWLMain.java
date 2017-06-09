package com.app.agnie.owl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class OWLMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owlmain);
        setupLayout();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupLayout(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String language = preferences.getString("interface_language_preference", "english");
        System.out.println("selected language: " + language);
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
