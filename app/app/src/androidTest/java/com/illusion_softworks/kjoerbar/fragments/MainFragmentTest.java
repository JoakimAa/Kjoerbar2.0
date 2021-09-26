package com.illusion_softworks.kjoerbar.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUpActivityScenario() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void navigateToUnitCatalogButton() {
        onView(withId(R.id.navigate_to_unit_catalog_button)).perform(click());
        onView(withId(R.id.btnAddAlcoholUnit)).perform(click());
    }

    @Test
    public void navigateToFriendsButton() {
        onView(withId(R.id.navigate_to_friends_button)).perform(click());
    }

    @Test
    public void navigateToHistoryButton() {
        onView(withId(R.id.navigate_to_history_button)).perform(click());
    }

    @Test
    public void navigateToSessionButton() {
        onView(withId(R.id.navigate_to_session_button)).perform(click());
    }

    /*@Test
    public void logOutButton() {
        onView(withId(R.id.btnLogOut)).perform(click());
    }*/

}
