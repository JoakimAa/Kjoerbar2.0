package com.illusion_softworks.kjoerbar.calculation;

import android.util.Log;

import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.User;

import java.util.ArrayList;

public class Calculations {
    public static double calculatePerMillPerUnit(User user, Drink drink, double time) {
        double perMillPerUnit;
        if (user.getGender().equals("Male")) {
            perMillPerUnit = drink.getGramAlcoholPerUnit() / (user.getWeight() * 0.7) - (0.15 * time);
        } else {
            perMillPerUnit = drink.getGramAlcoholPerUnit() / ((user.getWeight() * 0.6) - (0.15 * time));
        }
        return perMillPerUnit < 0 ? 0 : perMillPerUnit;
    }

    public static double calculateMaxPerMill(Double maxPerMill, Double currentPerMill) {
        if (currentPerMill > maxPerMill)
            maxPerMill = currentPerMill;
        return maxPerMill;
    }

    public static double calculateCurrentPerMill(ArrayList<AlcoholUnit> alcoholUnits, User user, long currentTime) {
        double currentPerMill = 0;
        for (AlcoholUnit alcoholUnit : alcoholUnits) {
            currentPerMill += calculatePerMillPerUnit(user, alcoholUnit.getDrink(), (double) ((currentTime - alcoholUnit.getTimeAddedToSession()) / 1000) / 3600);
            Log.d("currentTime_calculation", String.valueOf((double) ((currentTime - alcoholUnit.getTimeAddedToSession()) / 1000) / 3600));
            Log.d("currentTime_calculation_currentPerMill", String.valueOf(((currentPerMill))));
        }
        return currentPerMill;
    }

    public static long calculateTimeUntilSober(Double currentPerMill) {
        long timeUntilSober = (long) ((currentPerMill / 0.15) * 60 * 60 * 1000);
        Log.d("calculateTimeUntilSober", String.valueOf(timeUntilSober));
        Log.d("calculateTimeUntilSober", String.valueOf(currentPerMill));
        Log.d("calculateTimeUntilSober", String.valueOf((currentPerMill / 0.15)));
        return timeUntilSober;
    }
}
