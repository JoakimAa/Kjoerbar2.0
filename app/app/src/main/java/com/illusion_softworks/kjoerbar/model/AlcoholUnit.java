package com.illusion_softworks.kjoerbar.model;

import java.time.LocalDateTime;

public class AlcoholUnit {
    private final Beverage beverage;
    private final LocalDateTime timeAddedToSession;

    public AlcoholUnit(Beverage beverage) {
        this.beverage = beverage;
        this.timeAddedToSession = LocalDateTime.now();
    }

    public LocalDateTime getTimeAddedToSession() {
        return timeAddedToSession;
    }

    public Beverage getBeverage() {
        return beverage;
    }
}
