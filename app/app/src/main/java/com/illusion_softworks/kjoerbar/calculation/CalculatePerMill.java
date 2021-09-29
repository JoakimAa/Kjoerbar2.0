package com.illusion_softworks.kjoerbar.calculation;

import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.User;

public class CalculatePerMill {
    public static double calculatePerMillPerUnit(User user, AlcoholUnit alcoholUnit, double time) {
        double perMill = 0;
        if (user.getGender().equals("Male")) {
            perMill = alcoholUnit.getGramAlcoholPerUnit() / (user.getWeight() * 0.7) - (0.15 * time);

        }
        if (user.getGender().equals("Female")) {
            perMill = alcoholUnit.getGramAlcoholPerUnit() / ((user.getWeight() * 0.6) - (0.15 * time));
        }

        return perMill;
    }
}
