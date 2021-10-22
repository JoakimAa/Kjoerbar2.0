package com.illusion_softworks.kjoerbar.calculation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculationsTest {

    @Test
    @DisplayName("Calculate per mill")
    void testCalculatePerMill() {

        // Setup
        final User user = new User("uid", 100, 90, 20, "Male", "Ole");
        final AlcoholUnit alcoholUnit = new AlcoholUnit(new Beverage("Beer", "producer", "category", 0.5, 40));

        // Run the test
        final double result = Calculations.calculatePerMillPerUnit(user, alcoholUnit.getBeverage(), 10);

        // Verify the results
        assertEquals(0.7549714285714288, result, 0.0001);
    }
}
