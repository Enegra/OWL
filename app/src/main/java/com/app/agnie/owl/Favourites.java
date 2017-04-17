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

import com.app.agnie.owl.Adapters.TabsPagerAdapter;
import com.app.agnie.owl.Fragments.FavouritesPageOne;
import com.app.agnie.owl.Fragments.FavouritesPageTwo;

public class Favourites extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        setupLayout();
    }

    private void setupLayout() {
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
        tabsPagerAdapter.addFragment(new FavouritesPageOne(), "Words");
        tabsPagerAdapter.addFragment(new FavouritesPageTwo(), "Lessons");
        viewPager.setAdapter(tabsPagerAdapter);
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.favourites_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_favourites);
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


}
