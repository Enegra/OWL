package com.app.agnie.owl;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.agnie.owl.Fragments.DictionaryItemDetailFragment;
import com.app.agnie.owl.Util.DictionaryEntry;

public class DictionaryItemDetail extends AppCompatActivity {

    DictionaryEntry selectedEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedEntry = getIntent().getParcelableExtra("selectedEntry");
        setContentView(R.layout.activity_dictionary_item_detail);
        setupLayout();
    }


    private void setupLayout() {
        setupToolbar();
        DictionaryItemDetailFragment detailFragment = new DictionaryItemDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("selectedEntry", selectedEntry);
        detailFragment.setArguments(arguments);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dictionary_item_frame_placeholder, detailFragment);
        fragmentTransaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.dictionary_item_detail_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.item_detail_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_favorite:
                //// TODO: 3/22/2017
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
