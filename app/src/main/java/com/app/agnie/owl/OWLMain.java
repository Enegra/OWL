package com.app.agnie.owl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class OWLMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owlmain);
    }

    public void enterDictionary(View view){
        Intent intent = new Intent(this, Dictionary.class);
        startActivity(intent);
    }

}
