package com.illusion_softworks.kjoerbar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.illusion_softworks.kjoerbar.model.Drink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionFragmentTest {

    private SessionFragment sessionFragmentUnderTest;

    @BeforeEach
    void setUp() {
        sessionFragmentUnderTest = new SessionFragment();
    }

    @Test
    void testOnCreate() {
        // Setup
        final Bundle savedInstanceState = new Bundle(0);

        // Run the test
        sessionFragmentUnderTest.onCreate(savedInstanceState);

        // Verify the results
    }

    @Test
    void testOnCreateView() {
        // Setup
        final LayoutInflater inflater = LayoutInflater.from(null);
        final ViewGroup container = null;
        final Bundle savedInstanceState = new Bundle(0);

        // Run the test
        final View result = sessionFragmentUnderTest.onCreateView(inflater, container, savedInstanceState);

        // Verify the results
    }

    @Test
    void testOnViewCreated() {
        // Setup
        final View view = new View(null);
        final Bundle savedInstanceState = new Bundle(0);

        // Run the test
        sessionFragmentUnderTest.onViewCreated(view, savedInstanceState);

        // Verify the results
    }

    @Test
    void testUpdatePerMill() {
        // Setup
        // Run the test
        sessionFragmentUnderTest.updatePerMill();

        // Verify the results
    }

    @Test
    void testOnItemClick1() {
        // Setup
        // Run the test
        sessionFragmentUnderTest.onItemClick(0);

        // Verify the results
    }

    @Test
    void testOnItemClick2() {
        // Setup
        // Run the test
        sessionFragmentUnderTest.onItemClick("view", 0);

        // Verify the results
    }

    @Test
    void testStartNewSession() {
        // Setup
        // Run the test
        SessionFragment.startNewSession("name");

        // Verify the results
    }

    @Test
    void testAddDrink() {
        // Setup
        final Drink drink = new Drink("name", "producer", "category", 0.0, 0.0);

        // Run the test
        SessionFragment.addDrink(drink);

        // Verify the results
    }
}
