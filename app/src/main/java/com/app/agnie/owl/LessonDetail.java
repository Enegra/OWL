package com.app.agnie.owl;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.app.agnie.owl.Fragments.DictionaryItemDetailFragment;
import com.app.agnie.owl.Util.FavouritePreference;
import com.app.agnie.owl.Util.Lesson;

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
        //// TODO: 4/13/2017 fix fragment
        setupToolbar();
        setupDrawer();
        DictionaryItemDetailFragment detailFragment = new DictionaryItemDetailFragment();
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

    private void setupDrawer(){
        drawerLayout = (DrawerLayout)findViewById(R.id.lesson_detail_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_lessons);
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
}
