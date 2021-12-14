package com.illusion_softworks.kjoerbar.model;

public class AlcoholUnit {
    private Drink drink;
    private long timeAddedToSession;

    public AlcoholUnit() {
    }

    public AlcoholUnit(Drink drink) {
        this.drink = drink;
        this.timeAddedToSession = System.currentTimeMillis();
    }

    public long getTimeAddedToSession() {
        return timeAddedToSession;
    }

    public Drink getDrink() {
        return drink;
    }
}
