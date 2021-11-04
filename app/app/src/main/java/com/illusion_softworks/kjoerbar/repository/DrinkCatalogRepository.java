package com.illusion_softworks.kjoerbar.repository;

import androidx.lifecycle.MutableLiveData;

import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class DrinkCatalogRepository {
    private static DrinkCatalogRepository sInstance;
    private ArrayList<Drink> mDataSet = new ArrayList<>();

    public static DrinkCatalogRepository getInstance() {
        if (sInstance == null) {
            sInstance = new DrinkCatalogRepository();
        }
        return sInstance;
    }

    public MutableLiveData<List<Drink>> getDrinks() {
        // Database queries

        setDrinksWithDummyData();
        MutableLiveData<List<Drink>> data = new MutableLiveData<>();
        data.setValue(mDataSet);

        return data;
    }

    private void setDrinksWithDummyData() {
        mDataSet.add(new Drink("Whiskey", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Wine", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Rum", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Beer", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Bruh", "Rum", "cl", 200, 40));
    }
}
