package com.app.agnie.owl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.agnie.owl.Adapters.TabsPagerAdapter;
import com.app.agnie.owl.Fragments.DictionaryPageOne;
import com.app.agnie.owl.Fragments.DictionaryPageTwo;
import com.app.agnie.owl.Util.DictionaryDataSource;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.DictionaryEntryHandler;

import java.util.ArrayList;


public class Dictionary extends AppCompatActivity implements DictionaryEntryHandler {

    ViewPager viewPager;
    ArrayList<DictionaryEntry> dictionaryEntries;
    DictionaryDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDictionary();
        setContentView(R.layout.activity_dictionary);
        setupLayout();
        dataSource = new DictionaryDataSource(this);
        dataSource.open();
        dataSource.createInitialValues(this);
        dataSource.getDictionaryEntries("polish", "english");
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

    private void setupLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.dictionary_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.dictionary_viewpager);
        setupTabPager(viewPager);
        TabLayout tabs = (TabLayout) findViewById(R.id.dictionary_tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupTabPager(ViewPager viewPager) {
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabsPagerAdapter.addFragment(new DictionaryPageOne(), "Daily Screech");
        tabsPagerAdapter.addFragment(new DictionaryPageTwo(), "Dictionary List");
        viewPager.setAdapter(tabsPagerAdapter);
    }

    private void setupDictionary() {
        dictionaryEntries = new ArrayList<>();
        DictionaryEntry mug = new DictionaryEntry("mug.png", "Kubek", "Mug", new String[]{"Ten kubek jest brudny", "Mój ulubiony kubek jest niebieski", "Zbiłeś mój kubek!"}, new String[]{"This mug is dirty", "My favourite mug is blue", "You broke my mug!"});
        DictionaryEntry tea = new DictionaryEntry("tea.png", "Herbata", "Tea", new String[]{"Mam ochotę na herbatę", "Lubię zieloną herbatę"}, new String[]{"I feel like having some tea", "I like green tea"});
        dictionaryEntries.add(mug);
        dictionaryEntries.add(tea);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dictionary, menu);
        return true;
    }

    @Override
    public DictionaryEntry getDictionaryEntry(int index) {
        return dictionaryEntries.get(index);
    }

    @Override
    public int getEntriesCount() {
        return dictionaryEntries.size();
    }

    @Override
    public boolean isEmpty() {
        return dictionaryEntries.isEmpty();
    }

    @Override
    public int getImageID(int index) {
        String[] fileName = dictionaryEntries.get(index).getImage().split("\\.");
        String imageName = fileName[0];
        return getResources().getIdentifier(imageName, "drawable", getPackageName());
    }

    @Override
    public ArrayList<DictionaryEntry> getDictionaryEntries() {
        return dictionaryEntries;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


