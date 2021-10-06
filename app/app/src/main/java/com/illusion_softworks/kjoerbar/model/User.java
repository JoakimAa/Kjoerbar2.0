package com.illusion_softworks.kjoerbar.model;

import com.google.firebase.firestore.Exclude;

public class User {
    @Exclude
    private String uid;
    private int weight, height, age;
    private String gender, username;
    private Preferences preferences;
    private boolean isToSAccepted = false;
    private Session currentSession;

    public User() {
    }

    public User(int weight, int height, int age, String gender, String username) {
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.username = username;
    }

    public User(String uid, int weight, int height, int age, String gender, String username) {
        this(weight, height, age, gender, username);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
