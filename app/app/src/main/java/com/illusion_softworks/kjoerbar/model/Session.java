package com.illusion_softworks.kjoerbar.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Session {
    private SessionLimit sessionLimit = null;
    private ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();
    private int userWeight;
    private String userGender, name;
    private final LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime endDateTime = null;
    private double maxPerMill, currentPerMill = 0;

    public Session() {
    }

    public Session(int userWeight, String userGender) {
        this.userWeight = userWeight;
        this.userGender = userGender;
    }

    public Session(SessionLimit sessionLimit, ArrayList<AlcoholUnit> alcoholUnits, int userWeight, String userGender) {
        this(userWeight, userGender);
        this.sessionLimit = sessionLimit;
        this.alcoholUnits = alcoholUnits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getMaxPerMill() {
        return maxPerMill;
    }

    public void setMaxPerMill(double maxPerMill) {
        this.maxPerMill = maxPerMill;
    }

    public double getCurrentPerMill() {
        return currentPerMill;
    }

    public void setCurrentPerMill(double currentPerMill) {
        this.currentPerMill = currentPerMill;
    }

    public SessionLimit getSessionLimit() {
        return sessionLimit;
    }

    public void setCalculationLimit(SessionLimit sessionLimit) {
        this.sessionLimit = sessionLimit;
    }

    public ArrayList<AlcoholUnit> getAlcoholUnits() {
        return new ArrayList<AlcoholUnit>(alcoholUnits);
    }

    public void addAlcoholUnit(AlcoholUnit alcoholUnit) {
        alcoholUnits.add(alcoholUnit);
    }

    public void removeAlcoholUnit(AlcoholUnit alcoholUnit) {
        alcoholUnits.remove(alcoholUnit);
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<Beverage> getBeverages() {
        List<Beverage> beverages = new ArrayList<>();
        for (AlcoholUnit alcoholUnit : getAlcoholUnits()) {
            beverages.add(alcoholUnit.getBeverage());
        }
        return beverages;
    }
}

