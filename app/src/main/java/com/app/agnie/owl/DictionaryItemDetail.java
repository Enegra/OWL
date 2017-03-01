package com.app.agnie.owl;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.app.agnie.owl.Fragments.DictionaryItemDetailFragment;

public class DictionaryItemDetail extends AppCompatActivity {

    int entryIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entryIndex = getIntent().getIntExtra("index", 0);
        setContentView(R.layout.activity_dictionary_item_detail);
        setupLayout();
    }


    private void setupLayout(){
        setupToolbar();
        DictionaryItemDetailFragment detailFragment = new DictionaryItemDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt("entryIndex", entryIndex);
        detailFragment.setArguments(arguments);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dictionary_item_frame_placeholder, detailFragment);
        fragmentTransaction.commit();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.dictionary_item_detail_toolbar);
        setSupportActionBar(toolbar);
    }
}
