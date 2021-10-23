package com.illusion_softworks.kjoerbar.model;

public class Beverage {
    private final String name, producer, category;
    private final double percent, amount;
    private double gramAlcoholPerUnit;

    public Beverage(String name, String producer, String category, double amount, double percent) {
        this.name = name;
        this.producer = producer;
        this.category = category;
        this.amount = amount;
        this.percent = percent;
        calculateGramAlcoholPerUnit();
    }
    public Beverage(String name, String category, double amount, double percent) {
        this(name, "Not Specified", category, amount, percent);
        //calculateGramAlcoholPerUnit();
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
