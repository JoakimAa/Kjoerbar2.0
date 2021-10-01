package com.illusion_softworks.kjoerbar.activities;

import android.os.Bundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SettingsActivityTest {

    private SettingsActivity settingsActivityUnderTest;

    @BeforeEach
    void setUp() {
        settingsActivityUnderTest = new SettingsActivity();
    }

    @Test
    void testOnBackPressed() {
        // Setup

        // Run the test
        settingsActivityUnderTest.onBackPressed();

        // Verify the results
    }

    @Test
    void testOnCreate() {
        // Setup
        final Bundle savedInstanceState = new Bundle(0);

        // Run the test
        settingsActivityUnderTest.onCreate(savedInstanceState);

        // Verify the results
    }
}
