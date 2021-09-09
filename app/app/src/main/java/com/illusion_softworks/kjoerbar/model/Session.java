package com.illusion_softworks.kjoerbar.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.UUID;

public class Session {
    UUID uuid = UUID.randomUUID();
    @Exclude
    private String uid = uuid.toString();
    private SessionLimit sessionLimit;
    private ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();
    private int userWeight;
    private String userGender;

    public Session() {}

    public Session(int userWeight, String userGender) {
        this.userWeight = userWeight;
        this.userGender = userGender;
    }

    public Session(String uid, SessionLimit sessionLimit, ArrayList<AlcoholUnit> alcoholUnits, int userWeight, String userGender) {
        this.uid = uid;
        this.sessionLimit = sessionLimit;
        this.alcoholUnits = alcoholUnits;
        this.userWeight = userWeight;
        this.userGender = userGender;
    }

    @Exclude
    public String getUid() {
        return uid;
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

    public int getUserWeight() {
        return userWeight;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
}

