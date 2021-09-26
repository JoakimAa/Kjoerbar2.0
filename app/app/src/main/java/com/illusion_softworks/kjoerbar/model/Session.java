package com.illusion_softworks.kjoerbar.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Session {
    private final LocalDateTime startDateTime = LocalDateTime.now();
    /*UUID uuid = UUID.randomUUID();
    @Exclude
    private String uid = uuid.toString();*/
    private SessionLimit sessionLimit;
    private ArrayList<SessionAlcoholUnit> alcoholUnits = new ArrayList<>();
    private int userWeight;
    private String userGender;
    private LocalDateTime endDateTime;
    private double maxPerMill, currentPerMill;

    public Session() {
    }

    public Session(int userWeight, String userGender) {
        this.userWeight = userWeight;
        this.userGender = userGender;
    }

    public Session(SessionLimit sessionLimit, ArrayList<SessionAlcoholUnit> alcoholUnits, int userWeight, String userGender) {
        this(userWeight, userGender);
        this.sessionLimit = sessionLimit;
        this.alcoholUnits = alcoholUnits;
    }

    /*public Session(String uid, SessionLimit sessionLimit, ArrayList<AlcoholUnit> alcoholUnits, int userWeight, String userGender) {
        this(sessionLimit, alcoholUnits, userWeight, userGender);
        this.uid = uid;
    }*/

    /*@Exclude
    public String getUid() {
        return uid;
    }*/

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

    public void addAlcoholUnit(SessionAlcoholUnit alcoholUnit) {
        alcoholUnits.add(alcoholUnit);
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
}

