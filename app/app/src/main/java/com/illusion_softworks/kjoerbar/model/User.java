package com.illusion_softworks.kjoerbar.model;

import com.google.firebase.firestore.Exclude;

public class User {
    @Exclude
    private String uid;
    private int weight, height, age;
    private String gender;

    public User() { }

    public User(String uid, int weight, int height, int age, String gender) {
        this.uid = uid;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.gender = gender;
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


}
