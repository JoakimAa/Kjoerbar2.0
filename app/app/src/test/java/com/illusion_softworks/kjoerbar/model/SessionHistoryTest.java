package com.illusion_softworks.kjoerbar.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SessionHistoryTest {

    private SessionHistory sessionHistoryUnderTest;

    @BeforeEach
    void setUp() {
        sessionHistoryUnderTest = new SessionHistory();
    }

    @Test
    @DisplayName("Add session to history")
    void testAddSession() {
        // Setup
        final Session session = new Session(0, "userGender");

        // Run the test
        sessionHistoryUnderTest.addSession(session);

        // Verify the results
    }

    @Test
    @DisplayName("Remove session from history")
    void testRemoveSession() {
        // Setup
        final Session session = new Session(0, "userGender");

        // Run the test
        sessionHistoryUnderTest.removeSession(session);

        // Verify the results
    }
}
