package com.illusion_softworks.kjoerbar.model;

import java.time.LocalDateTime;

public class SessionAlcoholUnit extends AlcoholUnit {
    private final LocalDateTime timeAddedToSession;

    public SessionAlcoholUnit(String name, String producer, String category, String amountType, double amount, double percent, LocalDateTime timeAddedToSession) {
        super(name, producer, category, amountType, amount, percent);
        this.timeAddedToSession = timeAddedToSession;
    }

    public SessionAlcoholUnit(AlcoholUnit alcoholUnit, LocalDateTime timeAddedToSession) {
        this(alcoholUnit.getName(), alcoholUnit.getProducer(), alcoholUnit.getCategory(), alcoholUnit.getAmountType(), alcoholUnit.getAmount(), alcoholUnit.getPercent(), timeAddedToSession);
    }

    public LocalDateTime getTimeAddedToSession() {
        return timeAddedToSession;
    }
}
