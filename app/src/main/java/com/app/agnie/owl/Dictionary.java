package com.app.agnie.owl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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
import com.app.agnie.owl.Util.DictionaryDataSource;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.DictionaryEntryHandler;

import java.util.ArrayList;


public class Dictionary extends AppCompatActivity implements DictionaryEntryHandler {

    private ArrayList<DictionaryEntry> dictionaryEntries;
//    private DictionaryDataSource dataSource;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        setupLayout();
        setDataSource();
    }

    private void setupLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.dictionary_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setupDrawer();
    }

    private void setupTabs(){
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

    private void setDataSource(){
        new DictionaryRetrievalTask().execute();
    }

    private class DictionaryRetrievalTask extends AsyncTask<Void, Void, Void>{

        ProgressDialog progressDialog = new ProgressDialog(Dictionary.this);

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog.setMessage("\tRetrieving data...");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            DictionaryDataSource dataSource = new DictionaryDataSource(Dictionary.this);
            dataSource.open();
            dataSource.createInitialValues(Dictionary.this);
            dictionaryEntries = dataSource.getDictionaryEntries("polish", "english");
            dataSource.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            setupTabs();
            progressDialog.dismiss();
        }
    }

    private void setupDrawer(){
        drawerLayout = (DrawerLayout)findViewById(R.id.dictionary_drawer_layout);
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

    private void selectDrawerItem(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default, menu);
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
            case R.id.action_menu:
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


