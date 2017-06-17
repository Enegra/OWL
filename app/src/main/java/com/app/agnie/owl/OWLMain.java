package com.app.agnie.owl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.agnie.owl.Util.SingletonSession;

public class OWLMain extends AppCompatActivity {

    String[] interfaceStrings;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owlmain);
        setupLayout();
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
            interfaceResources[5] = R.array.navigation_english_interface;
        } else if (interfaceLanguage.equals("polish")){
            interfaceResources[0] = R.array.main_polish_interface;
            interfaceResources[1] = R.array.dictionary_polish_interface;
            interfaceResources[2] = R.array.favourites_polish_interface;
            interfaceResources[3] = R.array.lessons_polish_interface;
            interfaceResources[4] = R.array.tests_polish_interface;
            interfaceResources[5] = R.array.navigation_polish_interface;
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
        setTitle(interfaceStrings[4]);
    }

    private void setupLayout(){
        setupInterfaceTitles();
        TextView header = (TextView)findViewById(R.id.owlmain_header);
        Typeface marker = Typeface.createFromAsset(getAssets(), "fonts/PermanentMarker.ttf");
        header.setTypeface(marker);
        setupDrawer();
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

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
        if (!SingletonSession.Instance().getInterfaceLanguage().equals("english")) {
            changeMenuLanguage(navigationView);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void changeMenuLanguage(NavigationView navigationView) {
        String[] menuStrings = getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[5]);
        Menu menu = navigationView.getMenu();
        MenuItem home = menu.findItem(R.id.nav_home);
        home.setTitle(menuStrings[0]);
        MenuItem dictionary = menu.findItem(R.id.nav_dictionary);
        dictionary.setTitle(menuStrings[1]);
        MenuItem lessons = menu.findItem(R.id.nav_lessons);
        lessons.setTitle(menuStrings[2]);
        MenuItem tests = menu.findItem(R.id.nav_tests);
        tests.setTitle(menuStrings[3]);
        MenuItem favourites = menu.findItem(R.id.nav_lessons);
        favourites.setTitle(menuStrings[4]);
        MenuItem settings = menu.findItem(R.id.nav_settings);
        settings.setTitle(menuStrings[5]);
        View headerLayout = navigationView.getHeaderView(0);
        TextView subtitle = (TextView) headerLayout.findViewById(R.id.nav_subtitle);
        subtitle.setText(menuStrings[6]);
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


}
