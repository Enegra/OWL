package com.app.agnie.owl;

import android.content.Intent;
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
import android.view.View;
import android.widget.TextView;

import com.app.agnie.owl.Adapters.TabsPagerAdapter;
import com.app.agnie.owl.Fragments.FavouritesPageOne;
import com.app.agnie.owl.Fragments.FavouritesPageTwo;
import com.app.agnie.owl.Util.SingletonSession;

public class Favourites extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private String[] interfaceStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setupLayout();
    }

    private void setupLayout() {
        interfaceStrings = getResources().getStringArray(SingletonSession.Instance().getInterfaceResources()[2]);
        setTitle(interfaceStrings[0]);
        Toolbar toolbar = (Toolbar) findViewById(R.id.favourites_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setupDrawer();
        ViewPager viewPager = (ViewPager) findViewById(R.id.favourites_viewpager);
        setupTabPager(viewPager);
        TabLayout tabs = (TabLayout) findViewById(R.id.favourites_tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupTabPager(ViewPager viewPager) {
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        tabsPagerAdapter.addFragment(new FavouritesPageOne(), interfaceStrings[1]);
        tabsPagerAdapter.addFragment(new FavouritesPageTwo(), interfaceStrings[2]);
        viewPager.setAdapter(tabsPagerAdapter);
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.favourites_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_favourites);
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


}
