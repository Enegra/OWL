package com.app.agnie.owl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.app.agnie.owl.Adapters.LessonTileAdapter;
import com.app.agnie.owl.Util.DBUtil.DBConfig;
import com.app.agnie.owl.Util.DBUtil.DataSource;
import com.app.agnie.owl.Util.DBUtil.RequestHandler;
import com.app.agnie.owl.Util.Lesson;

import java.util.ArrayList;

public class Lessons extends AppCompatActivity {

    private ArrayList<Lesson> lessons;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        prepareLessons();
        setupLayout();
    }

    private void setupLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.lessons_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setupDrawer();
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

    private void setupGrid() {
        LessonTileAdapter adapter = new LessonTileAdapter(getApplicationContext(), lessons);
        RecyclerView lessonList = (RecyclerView) findViewById(R.id.lessons_list);
        lessonList.setAdapter(adapter);
        lessonList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lessonList.setHasFixedSize(true);
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.lessons_drawer_layout);
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

    private void prepareLessons() {
        LessonRetrievalTask task = new LessonRetrievalTask();
        task.execute();
    }

        private class LessonRetrievalTask extends AsyncTask<Void, Void, Void> {

            private ProgressDialog progressDialog = new ProgressDialog(Lessons.this);
            DataSource dataSource = new DataSource(getApplicationContext());
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("OWLData", 0);



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("\tRetrieving data...");
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                setupGrid();
                progressDialog.dismiss();
            }

            @Override
            protected Void doInBackground(Void... params) {
                if (!preferences.getBoolean("lessons_fetched", false)){
                    RequestHandler requestHandler = new RequestHandler();
                    String lessonString =  requestHandler.sendGetRequest(DBConfig.URL_GET_LESSON);
                    dataSource.open();
                    dataSource.createInitialLessonValues(getApplicationContext(), lessonString);
                    dataSource.close();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("lessons_fetched", true);
                    editor.apply();
                }
                dataSource.open();
                lessons = dataSource.getLessons("german", "english");
                dataSource.close();
                return null;
            }

        }


}
