package com.app.agnie.owl;

import android.support.test.espresso.PerformException;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.app.agnie.owl.R.id.right;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class OwlEspressoTest {
    @Rule
    public ActivityTestRule<OWLMain> mActivityRule =
            new ActivityTestRule<>(OWLMain.class);

    @Test
    public void pressEverything() throws Exception {
        goThroughDictionary();
        goThroughLessons();
        goThroughTests();
        goThroughFavourites();
        goThroughSettings();
    }

    private void goThroughDictionary() {
        onView(allOf(withId(R.id.main_dictionary_button))).perform(click());
        onView(allOf(withId(R.id.dictionary_screech_image))).perform(swipeRight());
        onView(allOf(withId(R.id.dictionary_screech_image))).perform(swipeUp());
        onView(allOf(withId(R.id.dictionary_screech_image))).perform(swipeDown());
        onView(allOf(withId(R.id.dictionary_screech_image))).perform(swipeUp());
        onView(allOf(withId(R.id.dictionary_screech_image))).perform(swipeLeft());
        onView(withId(R.id.dictionary_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        pressBack();
    }

    private void goThroughLessons() {
        onView(allOf(withId(R.id.main_lessons_button))).perform(click());
        onView(withId(R.id.lessons_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.lesson_detail_linear_layout)).perform(swipeUp());
        onView(withId(R.id.lesson_detail_linear_layout)).perform(swipeLeft());
        onView(withId(R.id.lesson_detail_linear_layout)).perform(swipeRight());
        onView(withId(R.id.lesson_detail_linear_layout)).perform(swipeDown());
        onView(withId(R.id.action_favorite)).perform(click());
        pressBack();
        onView(withId(R.id.lessons_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        pressBack();
        onView(withId(R.id.lessons_drawer_layout)).perform(DrawerActions.open(right));
        onView(withText("Home")).perform(click());
    }

    private void goThroughTests() throws Exception {
        onView(allOf(withId(R.id.main_tests_button))).perform(click());
        try {
            onView(withId(R.id.tests_scores_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        } catch (PerformException e) {
            onView(allOf(withId(R.id.tests_scores_list_textview))).perform(swipeLeft());
        }
        pressBack();
    }

    private void goThroughFavourites() {
        onView(allOf(withId(R.id.main_favourites_button))).perform(click());
        pressBack();
    }

    public void goThroughSettings() {
        onView(allOf(withId(R.id.action_menu))).perform(click());
        onView(withText("Settings")).perform(click());
        onView(allOf(withId(R.id.update_button))).perform(click());
        onView(allOf(withId(R.id.clear_favourites_button))).perform(click());
        onView(allOf(withId(R.id.clear_scores_button))).perform(click());
        onView(allOf(withId(R.id.action_menu))).perform(click());
        onView(withText("Home")).perform(click());
    }
}
