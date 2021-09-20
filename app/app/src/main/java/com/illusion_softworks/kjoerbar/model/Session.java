package com.illusion_softworks.kjoerbar.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.Exclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Session {
    /*UUID uuid = UUID.randomUUID();
    @Exclude
    private String uid = uuid.toString();*/
    private SessionLimit sessionLimit;
    private ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();
    private int userWeight;
    private String userGender;
    private final LocalDateTime startDateTime = LocalDateTime.now();
    private LocalDateTime endDateTime;

    public Session() {}

    public Session(int userWeight, String userGender) {
        this.userWeight = userWeight;
        this.userGender = userGender;
    }

    public Session(SessionLimit sessionLimit, ArrayList<AlcoholUnit> alcoholUnits, int userWeight, String userGender) {
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

    public LocalDateTime getLocalDateTime() {
        return startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}

