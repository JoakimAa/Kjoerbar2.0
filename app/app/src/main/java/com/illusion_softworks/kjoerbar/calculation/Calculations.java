package com.illusion_softworks.kjoerbar.calculation;

import android.util.Log;

import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Calculations {
    public static double calculatePerMillPerUnit(User user, Beverage beverage, double time) {
        if (user.getGender().equals("Male")) {
            return beverage.getGramAlcoholPerUnit() / (user.getWeight() * 0.7) - (0.15 * time);
        } else {
            return beverage.getGramAlcoholPerUnit() / ((user.getWeight() * 0.6) - (0.15 * time));
        }
    }

    public static double calculateMaxPerMill(Double maxPerMill, Double currentPerMill) {
        if (currentPerMill > maxPerMill)
            maxPerMill = currentPerMill;
        return maxPerMill;
    }

    public static double calculateCurrentPerMill(ArrayList<AlcoholUnit> alcoholUnits, User user, LocalDateTime currentTime) {

        double currentPerMill = 0;
        for (AlcoholUnit alcoholUnit : alcoholUnits) {
            currentPerMill += calculatePerMillPerUnit(user, alcoholUnit, (double) ChronoUnit.SECONDS.between(alcoholUnit.getTimeAddedToSession(), currentTime) / 3600);
            Log.d("currentTime", String.valueOf((double) ChronoUnit.SECONDS.between(alcoholUnit.getTimeAddedToSession(), currentTime) / 3600));
        }
        return currentPerMill;
    }

    public static long calculateTimeUntilSober(Double currentPerMill) {
        long timeUntilSober = new Double(currentPerMill / 0.15).longValue();
        Log.d("calculateTimeUntilSober", String.valueOf(timeUntilSober));
        Log.d("calculateTimeUntilSober", String.valueOf(currentPerMill));
        Log.d("calculateTimeUntilSober", String.valueOf((currentPerMill / 0.15)));
        Log.d("calculateTimeUntilSober", String.valueOf(TimeUnit.HOURS.toMillis(timeUntilSober)));
        return TimeUnit.HOURS.toMillis(timeUntilSober);
    }
}
