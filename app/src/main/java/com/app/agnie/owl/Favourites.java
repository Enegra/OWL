package com.app.agnie.owl;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.agnie.owl.Adapters.TabsPagerAdapter;
import com.app.agnie.owl.Fragments.DictionaryPageOne;
import com.app.agnie.owl.Fragments.DictionaryPageTwo;
import com.app.agnie.owl.Fragments.FavouritesPageOne;
import com.app.agnie.owl.Fragments.FavouritesPageTwo;

public class Favourites extends AppCompatActivity {

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
