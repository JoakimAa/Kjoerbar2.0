package com.illusion_softworks.kjoerbar.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AlcoholUnitTest {

    private AlcoholUnit alcoholUnitUnderTest;

    @BeforeEach
    void setUp() {
        alcoholUnitUnderTest = new AlcoholUnit("name", "producer", "category", "amountType", 0.0, 0.0, LocalDateTime.now());
    }

    @Test
    void testGetName() {
        assertEquals(alcoholUnitUnderTest.getName(), "name");
    }
}
