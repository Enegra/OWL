package com.app.agnie.owl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class OWLMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owlmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

}
