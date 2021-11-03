package com.illusion_softworks.kjoerbar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.List;

public class DrinkViewModel extends ViewModel {
    private MutableLiveData<List<Drink>> drinks;

    public LiveData<List<Drink>> getDrinks() {
        if (drinks == null) {
            drinks = new MutableLiveData<>();
            loadDrinks();
        }
        return drinks;
    }

    private void loadDrinks() {

    }
}
