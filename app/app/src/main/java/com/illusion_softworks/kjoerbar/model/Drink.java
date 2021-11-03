package com.illusion_softworks.kjoerbar.model;

public class Drink {
    private String name, producer, category;
    private double percent, volume;
    private double gramAlcoholPerUnit;

    public Drink() {
    }

    public Drink(String name, String producer, String category, double volume, double percent) {
        this.name = name;
        this.producer = producer;
        this.category = category;
        this.volume = volume;
        this.percent = percent;
        calculateGramAlcoholPerUnit();
    }

    public Drink(String name, String category, double volume, double percent) {
        this(name, "Not Specified", category, volume, percent);
    }

    private void calculateGramAlcoholPerUnit() {
        this.gramAlcoholPerUnit = volume * (percent / 100) * 789.24;
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

    public double getVolume() {
        return volume;
    }

    public double getPercent() {
        return percent;
    }

    public double getGramAlcoholPerUnit() {
        return gramAlcoholPerUnit;
    }
}
