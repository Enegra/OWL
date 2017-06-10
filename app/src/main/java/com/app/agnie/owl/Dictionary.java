package com.app.agnie.owl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.agnie.owl.Adapters.TabsPagerAdapter;
import com.app.agnie.owl.Fragments.DictionaryPageOne;
import com.app.agnie.owl.Fragments.DictionaryPageTwo;
import com.app.agnie.owl.Util.DBUtil.DBConfig;
import com.app.agnie.owl.Util.DBUtil.DataSource;
import com.app.agnie.owl.Util.DBUtil.RequestHandler;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.SingletonSession;

import java.util.ArrayList;
import java.util.Random;


public class Dictionary extends AppCompatActivity {

    private ArrayList<DictionaryEntry> dictionaryEntries;
    private DrawerLayout drawerLayout;
    private static final String FEATURED_ENTRY = "FEATURED_ENTRY";
    private int featuredEntry;
    private String[] interfaceStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        setupLayout();
        if (savedInstanceState != null) {
            featuredEntry = savedInstanceState.getInt(FEATURED_ENTRY);
        }
        if (SingletonSession.Instance().getDictionaryData() != null) {
            dictionaryEntries = SingletonSession.Instance().getDictionaryData();
            setupTabs();
        } else {
            setDataSource();
            SingletonSession.Instance().setDictionaryData(dictionaryEntries);
        }
    }

    private void setupLayout() {
        interfaceStrings = getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[1]);
        setTitle(interfaceStrings[0]);
        Toolbar toolbar = (Toolbar) findViewById(R.id.dictionary_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setupDrawer();
    }

    private void setupTabs() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.dictionary_viewpager);
        setupTabPager(viewPager);
        TabLayout tabs = (TabLayout) findViewById(R.id.dictionary_tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupTabPager(ViewPager viewPager) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(fragmentManager);
        Bundle bundle = new Bundle();
        Random random = new Random();
        featuredEntry = random.nextInt(dictionaryEntries.size());
        bundle.putInt(FEATURED_ENTRY, featuredEntry);
        DictionaryPageOne dictionaryPageOne = new DictionaryPageOne();
        dictionaryPageOne.setArguments(bundle);
        tabsPagerAdapter.addFragment(dictionaryPageOne, interfaceStrings[1]);
        DictionaryPageTwo dictionaryPageTwo = new DictionaryPageTwo();
        tabsPagerAdapter.addFragment(dictionaryPageTwo, interfaceStrings[2]);
        viewPager.setAdapter(tabsPagerAdapter);
    }

    private void setDataSource() {
        new DictionaryRetrievalTask().execute();
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.dictionary_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_dictionary);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_home:
                intent = new Intent(this, OWLMain.class);
                startActivity(intent);
                break;
            case R.id.nav_dictionary:
                intent = new Intent(this, Dictionary.class);
                startActivity(intent);
                break;
            case R.id.nav_favourites:
                intent = new Intent(this, Favourites.class);
                startActivity(intent);
                break;
            case R.id.nav_lessons:
                intent = new Intent(this, Lessons.class);
                startActivity(intent);
                break;
            case R.id.nav_tests:
                intent = new Intent(this, Tests.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_menu:
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FEATURED_ENTRY, featuredEntry);
    }

    private class DictionaryRetrievalTask extends AsyncTask<Void, Void, Void> {
        DataSource dataSource = new DataSource(getApplicationContext());
        ProgressDialog progressDialog = new ProgressDialog(Dictionary.this);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("OWLData", 0);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("\tRetrieving data...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setupTabs();
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<String> list = new ArrayList<>();
            if (!preferences.getBoolean("dictionary_fetched", false)) {
                RequestHandler requestHandler = new RequestHandler();
                String languages = requestHandler.sendGetRequest(DBConfig.URL_GET_LANGUAGE);
                String words = requestHandler.sendGetRequest(DBConfig.URL_GET_WORD);
                String sentences = requestHandler.sendGetRequest(DBConfig.URL_GET_SENTENCE);
                String descriptions = requestHandler.sendGetRequest(DBConfig.URL_GET_WORDDESCRIPTION);
                list.add(languages);
                list.add(words);
                list.add(descriptions);
                list.add(sentences);
                dataSource.open();
                dataSource.createInitialDictionaryValues(Dictionary.this, list);
                dataSource.close();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("dictionary_fetched", true);
                editor.apply();

            }
            dataSource.open();
            dictionaryEntries = dataSource.getDictionaryEntries(SingletonSession.Instance().getLearningLanguage(), SingletonSession.Instance().getInterfaceLanguage());
            SingletonSession.Instance().setDictionaryData(dictionaryEntries);
            dataSource.close();
            return null;
        }
    }
}


