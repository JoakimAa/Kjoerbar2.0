package com.illusion_softworks.kjoerbar.model;

import java.time.LocalDateTime;

public class AlcoholUnit {
    private final Drink drink;
    private final LocalDateTime timeAddedToSession;

    public AlcoholUnit(Drink drink) {
        this.drink = drink;
        this.timeAddedToSession = LocalDateTime.now();
    }

    public LocalDateTime getTimeAddedToSession() {
        return timeAddedToSession;
    }

    public Drink getDrink() {
        return drink;
    }
}
