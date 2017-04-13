package com.app.agnie.owl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.agnie.owl.Fragments.DictionaryItemDetailFragment;
import com.app.agnie.owl.Util.DictionaryEntry;
import com.app.agnie.owl.Util.FavouritePreference;

public class DictionaryItemDetail extends AppCompatActivity {

    private DictionaryEntry selectedEntry;
    private FavouritePreference favouritePreference;
    private boolean favourite;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritePreference = new FavouritePreference();
        selectedEntry = getIntent().getParcelableExtra("selectedEntry");
        setContentView(R.layout.activity_dictionary_item_detail);
        setupLayout();
    }


    private void setupLayout() {
        setupToolbar();
        setupDrawer();
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

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.item_detail_drawer_layout);
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_detail_toolbar, menu);
        setupFavouriteIcon(menu);
        return true;
    }

    private void setupFavouriteIcon(Menu menu) {
        if (favouritePreference.contains(getApplicationContext(), selectedEntry)) {
            favourite = true;
            menu.getItem(0).setIcon(R.drawable.ic_favorite_beige_48dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_favorite:
                if (favourite) {
                    item.setIcon(R.drawable.ic_favorite_border_beige_48dp);
                    favouritePreference.removeFavourite(getApplicationContext(), selectedEntry);
                    favourite = false;
                } else {
                    item.setIcon(R.drawable.ic_favorite_beige_48dp);
                    favouritePreference.addFavourite(getApplicationContext(), selectedEntry);
                    favourite = true;
                }
                return true;
            case R.id.action_menu:
                drawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
