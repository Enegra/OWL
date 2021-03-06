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
import android.view.View;
import android.widget.TextView;

import com.app.agnie.owl.Fragments.LessonDetailFragment;
import com.app.agnie.owl.Util.Entities.Lesson;
import com.app.agnie.owl.Util.FavouritePreference;
import com.app.agnie.owl.Util.SingletonSession;

public class LessonDetail extends AppCompatActivity {

    private Lesson selectedLesson;
    private FavouritePreference favouritePreference;
    private boolean favourite;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritePreference = new FavouritePreference();
        selectedLesson = getIntent().getParcelableExtra("selectedLesson");
        setContentView(R.layout.activity_lesson_detail);
        setupLayout();
    }

    private void setupLayout() {
        setupToolbar();
        setupDrawer();
        LessonDetailFragment detailFragment = new LessonDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable("selectedLesson", selectedLesson);
        detailFragment.setArguments(arguments);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.lesson_frame_placeholder, detailFragment);
        fragmentTransaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.lesson_detail_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.lesson_detail_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_lessons);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_detail_toolbar, menu);
        setupFavouriteIcon(menu);
        return true;
    }

    private void setupFavouriteIcon(Menu menu) {
        if (favouritePreference.contains(getApplicationContext(), selectedLesson)) {
            favourite = true;
            menu.getItem(0).setIcon(R.drawable.ic_favorite_beige_48dp);
        }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_favorite:
                if (favourite) {
                    item.setIcon(R.drawable.ic_favorite_border_beige_48dp);
                    favouritePreference.removeFavouriteLesson(getApplicationContext(), selectedLesson);
                    favourite = false;
                } else {
                    item.setIcon(R.drawable.ic_favorite_beige_48dp);
                    favouritePreference.addFavouriteLesson(getApplicationContext(), selectedLesson);
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
