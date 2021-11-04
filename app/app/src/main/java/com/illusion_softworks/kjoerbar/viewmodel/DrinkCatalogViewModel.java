package com.illusion_softworks.kjoerbar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.repository.DrinkCatalogRepository;

import java.util.Arrays;
import java.util.List;

public class DrinkCatalogViewModel extends ViewModel {
    private MutableLiveData<List<Drink>> mDrinks = new MutableLiveData<>();
    private DrinkCatalogRepository mRepository;

    public void init() {
        mRepository = DrinkCatalogRepository.getInstance();
        mDrinks = mRepository.getDrinks();
    }

    public LiveData<List<Drink>> getDrinks() {
        return mDrinks;
    }
}
