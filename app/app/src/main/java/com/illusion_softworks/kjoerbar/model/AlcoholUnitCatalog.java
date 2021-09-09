package com.illusion_softworks.kjoerbar.model;

import java.util.ArrayList;

public class AlcoholUnitCatalog {
    private ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();

    public ArrayList<AlcoholUnit> getAlcoholUnits() {
        return alcoholUnits;
    }

    public void setAlcoholUnits(ArrayList<AlcoholUnit> alcoholUnits) {
        this.alcoholUnits = alcoholUnits;
    }

    public void addAlcoholUnit(AlcoholUnit alcoholUnit) {
        alcoholUnits.add(alcoholUnit);
    }

    public void removeAlcohol(AlcoholUnit alcoholUnit) {
        alcoholUnits.remove(alcoholUnit);
    }
}
