package com.illusion_softworks.kjoerbar.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Session {
    private SessionLimit sessionLimit;
    private ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();
    private int userWeight;
    private String userGender, name;
    private long startTime, endTime;
    private double maxPerMill, currentPerMill;

    public Session() {
    }

    public Session(int userWeight, String userGender) {
        this.userWeight = userWeight;
        this.userGender = userGender;
        this.startTime = System.currentTimeMillis();
    }

    public Session(int userWeight, String userGender, SessionLimit sessionLimit, ArrayList<AlcoholUnit> alcoholUnits) {
        this(userWeight, userGender);
        this.sessionLimit = sessionLimit;
        this.alcoholUnits = alcoholUnits;
    }

    public Session(String name, int userWeight, String userGender) {
        this(userWeight, userGender);
        this.name = name;
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

    public void addAlcoholUnits(ArrayList<AlcoholUnit> alcoholUnit) {
        alcoholUnits.addAll(alcoholUnit);
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

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public List<Drink> getBeverages() {
        List<Drink> drinks = new ArrayList<>();
        for (AlcoholUnit alcoholUnit : getAlcoholUnits()) {
            drinks.add(alcoholUnit.getDrink());
        }
        return drinks;
    }
}

