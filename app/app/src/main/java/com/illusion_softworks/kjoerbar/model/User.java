package com.illusion_softworks.kjoerbar.model;

import com.google.firebase.firestore.Exclude;
import com.illusion_softworks.kjoerbar.datahandler.AlcoholUnitCatalogDataHandler;

public class User {
    @Exclude
    private String uid;
    private int weight, height, age;
    private String gender, name;
    private Preferences preferences;
    private boolean isToSAccepted = false;
    private Session currentSession;

    public User() { }

    public User(int weight, int height, int age, String gender, String name) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.name = name;
    }

    public User(String uid, int weight, int height, int age, String gender, String name) {
        this(weight, height, age, gender, name);
        this.uid = uid;
    }

    @Exclude
    public String getUid() {
        return uid;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public boolean isToSAccepted() {
        return isToSAccepted;
    }

    public void setToSAccepted(boolean toSAccepted) {
        isToSAccepted = toSAccepted;
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session session) {
        currentSession = session;
    }

}
