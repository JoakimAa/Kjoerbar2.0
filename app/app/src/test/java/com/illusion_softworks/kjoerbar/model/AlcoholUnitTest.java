package com.illusion_softworks.kjoerbar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AlcoholUnitTest {

    private Beverage beverageUnderTest;

    @BeforeEach
    void setUp() {
        beverageUnderTest = new Beverage("name", "producer", "category", 0.0, 0.0);
    }

    @Test
    @DisplayName("Get AlcoholUnit test")
    void testGetName() {
        assertEquals(beverageUnderTest.getName(), "name");
    }
}
