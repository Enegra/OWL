package com.app.agnie.owl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.agnie.owl.Util.DBUtil.DBConfig;
import com.app.agnie.owl.Util.DBUtil.DataSource;
import com.app.agnie.owl.Util.DBUtil.RequestHandler;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Settings extends AppCompatActivity{

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupLayout();
    }

    private void setupLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        setupDrawer();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.settings_frame_placeholder, new SettingsFragment());
        fragmentTransaction.commit();
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
        drawerLayout = (DrawerLayout) findViewById(R.id.settings_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_settings);
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

    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.settings_preferences);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = super.onCreateView(inflater, container, savedInstanceState);
            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.backgroundColour));
            setDivider(new ColorDrawable(Color.TRANSPARENT));
            setDividerHeight(0);
            return view;
        }

    }

    public void onUpdateButtonClick(View v) {
        CheckLatestVersion checker = new CheckLatestVersion();
        checker.execute();
        Toast.makeText(this, "Database updated", Toast.LENGTH_SHORT).show();
    }

    public void onClearFavouritesButtonClick(View v) {
        Log.d("Button", "Yeah, button was clicked");
    }

    public void onClearScoresButtonClick(View v) {
        Log.d("Button", "Yeah, button was clicked");
    }

    private class CheckLatestVersion extends AsyncTask<Void, Void, Void> {

        DataSource dataSource = new DataSource(getApplicationContext());
        ProgressDialog progressDialog = new ProgressDialog(Settings.this);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("OWLData", 0);
        boolean[] updatesAvailable = new boolean[8];
        ArrayList<ArrayList<String>> updatedDatabase;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("\tRetrieving latest version...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Date[] databaseDateArray = new Date[7];
            String[] preferencesArray = new String[7];

            RequestHandler requestHandler = new RequestHandler();
            databaseDateArray[0] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_WORDDATE));
            databaseDateArray[1] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_WORDDESCRIPTIONDATE));
            databaseDateArray[2] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_SENTENCEDATE));
            databaseDateArray[3] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_LESSONDATE));
            databaseDateArray[4] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_TESTDATE));
            databaseDateArray[5] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_QUESTIONDATE));
            databaseDateArray[6] = parseLatestDateFromJson(requestHandler.sendGetRequest(DBConfig.URL_GET_ANSWERDATE));

            preferencesArray[0] = preferences.getString("word_version_date", "1992-1-1");
            preferencesArray[1] = preferences.getString("description_version_date", "1992-1-1");
            preferencesArray[2] = preferences.getString("sentence_version_date", "1992-1-1");
            preferencesArray[3] = preferences.getString("lesson_version_date", "1992-1-1");
            preferencesArray[4] = preferences.getString("test_version_date", "1992-1-1");
            preferencesArray[5] = preferences.getString("question_version_date", "1992-1-1");
            preferencesArray[6] = preferences.getString("answer_version_date", "1992-1-1");

            SharedPreferences.Editor editor = preferences.edit();
            if (isNewer(preferencesArray[0], databaseDateArray[0])) {
                updatesAvailable[0] = true;
                editor.putString("word_version_date", dateToString(databaseDateArray[0]));
            }
            if (isNewer(preferencesArray[1], databaseDateArray[1])) {
                updatesAvailable[1] = true;
                editor.putString("description_version_date", dateToString(databaseDateArray[1]));
            }
            if (isNewer(preferencesArray[2], databaseDateArray[2])) {
                updatesAvailable[2] = true;
                editor.putString("sentence_version_date", dateToString(databaseDateArray[2]));
            }
            if (isNewer(preferencesArray[3], databaseDateArray[3])) {
                updatesAvailable[3] = true;
                editor.putString("lesson_version_date", dateToString(databaseDateArray[3]));
            }
            if (isNewer(preferencesArray[4], databaseDateArray[4])) {
                updatesAvailable[4] = true;
                editor.putString("test_version_date", dateToString(databaseDateArray[4]));
            }
            if (isNewer(preferencesArray[5], databaseDateArray[5])) {
                updatesAvailable[5] = true;
                editor.putString("question_version_date", dateToString(databaseDateArray[5]));
            }
            if (isNewer(preferencesArray[6], databaseDateArray[6])) {
                updatesAvailable[6] = true;
                editor.putString("answer_version_date", dateToString(databaseDateArray[6]));
            }
            editor.apply();
            updatedDatabase = getUpdatedDatabase(preferencesArray, updatesAvailable);
            dataSource.open();
            if(updatesAvailable[0] || updatesAvailable[1] || updatesAvailable[2]){
                dataSource.insertDictionaryValues(getApplicationContext(), updatedDatabase.get(0));
            }
            if(updatesAvailable[3]){
                dataSource.insertLessonValues(updatedDatabase.get(1).get(0));
            }
            if(updatesAvailable[4] || updatesAvailable[5] ||updatesAvailable[6]){
                dataSource.insertTestValues(updatedDatabase.get(2));
            }
            dataSource.close();
            return null;
        }
    }

    private ArrayList<ArrayList<String>> getUpdatedDatabase(String[] preferencesArray, boolean[] updatesAvailable) {
        ArrayList<ArrayList<String>> updatedDatabase = new ArrayList<>();
        ArrayList<String> dictionary = new ArrayList<>();
        ArrayList<String> lesson = new ArrayList<>();
        ArrayList<String> test = new ArrayList<>();

        RequestHandler requestHandler = new RequestHandler();
        String languages = requestHandler.sendGetRequest(DBConfig.URL_GET_LANGUAGE);
        dictionary.add(languages);
        if (updatesAvailable[0]) {
            String words = requestHandler.sendGetRequest(DBConfig.URL_GET_WORD + "?date=" + preferencesArray[0]);
            dictionary.add(words);
        }
        if (updatesAvailable[1]) {
            String descriptions = requestHandler.sendGetRequest(DBConfig.URL_GET_WORDDESCRIPTION + "?date=" + preferencesArray[1]);
            dictionary.add(descriptions);
        }
        if (updatesAvailable[2]) {
            String sentences = requestHandler.sendGetRequest(DBConfig.URL_GET_SENTENCE + "?date=" + preferencesArray[2]);
            dictionary.add(sentences);
        }
        if (updatesAvailable[3]) {
            String lessonString = requestHandler.sendGetRequest(DBConfig.URL_GET_LESSON + "?date=" + preferencesArray[3]);
            lesson.add(lessonString);
        }
        if (updatesAvailable[4]) {
            String testString = requestHandler.sendGetRequest(DBConfig.URL_GET_TEST + "?date=" + preferencesArray[4]);
            test.add(testString);
        }
        if (updatesAvailable[5]) {
            String questionString = requestHandler.sendGetRequest(DBConfig.URL_GET_QUESTION + "?date=" + preferencesArray[5]);
            test.add(questionString);
        }
        if (updatesAvailable[6]) {
            String answerString = requestHandler.sendGetRequest(DBConfig.URL_GET_ANSWER + "?date=" + preferencesArray[6]);
            test.add(answerString);
        }
        updatedDatabase.add(dictionary);
        updatedDatabase.add(lesson);
        updatedDatabase.add(test);

        return updatedDatabase;
    }

    private Date parseLatestDateFromJson(String jsonAnswerString) {
        JSONObject jsonObject;
        ArrayList<Date> dates = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            jsonObject = new JSONObject(jsonAnswerString);
            JSONArray result = jsonObject.getJSONArray(DBConfig.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String dateString = jo.getString(DBConfig.TAG_MODIFICATION_DATE);
                Date date = format.parse(dateString);
                dates.add(date);
            }
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        Date latestDate = dates.get(0);
        for (int i = 1; i < dates.size(); i++) {
            if (latestDate.before(dates.get(i))) {
                latestDate = dates.get(i);
            }
        }
        return latestDate;
    }

    private Date parseDateFromString(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date parsedDate = null;
        try {
            parsedDate = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }

    private String dateToString(Date date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateAsString = formatter.format(date);
        return dateAsString;
    }

    private boolean isNewer(String currentString, Date databaseDate) {
        Date currentDate = parseDateFromString(currentString);
        return databaseDate.after(currentDate);
    }

}
