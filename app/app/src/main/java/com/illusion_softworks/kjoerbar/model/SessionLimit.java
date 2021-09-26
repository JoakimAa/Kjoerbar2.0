package com.illusion_softworks.kjoerbar.model;

import java.time.LocalDateTime;

public class SessionLimit {
    private int maxAlcoholUnits;
    private double maxPerMill;
    private LocalDateTime drivableAt;

    public SessionLimit(int maxPerMill, int maxAlcoholUnits, LocalDateTime drivableAt) {
        this.maxPerMill = maxPerMill;
        this.maxAlcoholUnits = maxAlcoholUnits;
        this.drivableAt = drivableAt;
    }

    public double getMaxPerMill() {
        return maxPerMill;
    }

    public void setMaxPerMill(double maxPerMill) {
        this.maxPerMill = maxPerMill;
    }

    public int getMaxAlcoholUnits() {
        return maxAlcoholUnits;
    }

    public void setMaxAlcoholUnits(int maxAlcoholUnits) {
        this.maxAlcoholUnits = maxAlcoholUnits;
    }

    public LocalDateTime getDrivableAt() {
        return drivableAt;
    }

    public void setDrivableAt(LocalDateTime drivableAt) {
        this.drivableAt = drivableAt;
    }
}
