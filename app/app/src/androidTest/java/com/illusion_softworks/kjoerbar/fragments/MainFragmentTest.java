package com.illusion_softworks.kjoerbar.fragments;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.illusion_softworks.kjoerbar.activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;

public class MainFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    @Before
    public void setUpActivityScenario() {
        ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
    }

}
