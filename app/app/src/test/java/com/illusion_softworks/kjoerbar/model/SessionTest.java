package com.illusion_softworks.kjoerbar.model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;

class SessionTest {

    @Mock
    private SessionLimit mockSessionLimit;
    @Mock
    private ArrayList<SessionAlcoholUnit> mockAlcoholUnits;

    private Session sessionUnderTest;

    private AutoCloseable mockitoCloseable;

    @BeforeEach
    void setUp() {
        mockitoCloseable = openMocks(this);
        sessionUnderTest = new Session(mockSessionLimit, mockAlcoholUnits, 0, "userGender");
    }

    @AfterEach
    void tearDown() throws Exception {
        mockitoCloseable.close();
    }

    @Test
    @DisplayName("Set calculation limits")
    void testSetCalculationLimit() {
        // Setup
        final SessionLimit sessionLimit = new SessionLimit(0, 0, LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Run the test
        sessionUnderTest.setCalculationLimit(sessionLimit);

        // Verify the results
    }

    @Test
    @DisplayName("Add alcohol unit")
    void testAddAlcoholUnit() {
        // Setup
        final SessionAlcoholUnit alcoholUnit = new SessionAlcoholUnit("name", "producer", "category", "amountType", 0.0, 0.0, LocalDateTime.now());
        when(mockAlcoholUnits.add(any(SessionAlcoholUnit.class))).thenReturn(false);

        // Run the test
        sessionUnderTest.addAlcoholUnit(alcoholUnit);

        // Verify the results
        verify(mockAlcoholUnits).add(any(SessionAlcoholUnit.class));
    }
}
