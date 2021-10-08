package com.illusion_softworks.kjoerbar.calculation;

import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.User;

public class CalculatePerMill {
    public static double calculatePerMillPerUnit(User user, Beverage beverage, double time) {
        if (user.getGender().equals("Male")) {
            return beverage.getGramAlcoholPerUnit() / (user.getWeight() * 0.7) - (0.15 * time);
        } else {
            return beverage.getGramAlcoholPerUnit() / ((user.getWeight() * 0.6) - (0.15 * time));
        }
    }
}
