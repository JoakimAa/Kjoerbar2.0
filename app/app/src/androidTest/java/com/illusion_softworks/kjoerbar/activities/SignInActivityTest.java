package com.illusion_softworks.kjoerbar.activities;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SignInActivityTest {
    @Rule
    public ActivityScenarioRule<SignInActivity> activityScenarioRule = new ActivityScenarioRule<>(SignInActivity.class);

    @Before
    public void setUpActivityScenario() {
        ActivityScenario<SignInActivity> activityScenario = ActivityScenario.launch(SignInActivity.class);
    }

    @Test
    public void createSignInIntent() {
        activityScenarioRule.getScenario().onActivity(SignInActivity::createSignInIntent);
    }
}
