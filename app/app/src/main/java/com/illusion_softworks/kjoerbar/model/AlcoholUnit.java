package com.illusion_softworks.kjoerbar.model;

public class AlcoholUnit {
    private final String name, producer, category, amountType;
    private final double percent, amount;
    private double gramAlcoholPerUnit;

    public AlcoholUnit(String name, String producer, String category, String amountType, double amount, double percent) {
        this.name = name;
        this.producer = producer;
        this.category = category;
        this.amountType = amountType;
        this.amount = amount;
        this.percent = percent;
        calculateGramAlcoholPerUnit();
    }

    private void calculateGramAlcoholPerUnit() {
        this.gramAlcoholPerUnit = amount * (percent / 100) * 789.24;
    }

    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public String getCategory() {
        return category;
    }

    public String getAmountType() {
        return amountType;
    }

    public double getAmount() {
        return amount;
    }

    public double getPercent() {
        return percent;
    }

    public double getGramAlcoholPerUnit() {
        return gramAlcoholPerUnit;
    }
}
