package com.illusion_softworks.kjoerbar.model;

import java.time.LocalDateTime;

public class AlcoholUnit extends Beverage {
    private final LocalDateTime timeAddedToSession;

    public AlcoholUnit(String name, String producer, String category, String amountType, double amount, double percent, LocalDateTime timeAddedToSession) {
        super(name, producer, category, amount, percent);
        this.timeAddedToSession = timeAddedToSession;
    }

    public AlcoholUnit(Beverage beverage, LocalDateTime timeAddedToSession) {
        this(beverage.getName(), beverage.getProducer(), beverage.getCategory(), beverage.getAmountType(), beverage.getAmount(), beverage.getPercent(), timeAddedToSession);
    }

    public LocalDateTime getTimeAddedToSession() {
        return timeAddedToSession;
    }
}
