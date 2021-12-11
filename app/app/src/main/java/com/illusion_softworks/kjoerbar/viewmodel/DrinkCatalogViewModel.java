package com.illusion_softworks.kjoerbar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.repository.DrinkCatalogRepository;

import java.util.List;

public class DrinkCatalogViewModel extends ViewModel {
    private final MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private Drink mSelectedDrink;
    private MutableLiveData<List<Drink>> mDrinks;
    private DrinkCatalogRepository mRepository;

    public void init() {
        if (mDrinks != null) {
            return;
        }
        mIsUpdating.setValue(true);
        mRepository = DrinkCatalogRepository.getInstance();
        mDrinks = mRepository.getDrinks(() -> mIsUpdating.setValue(false));
    }

    public LiveData<List<Drink>> getDrinks() {
        return mDrinks;
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }

    public Drink getSelectedSession() {
        return mSelectedDrink;
    }

    public void setSelectedDrink(Drink selectedDrink) {
        this.mSelectedDrink = selectedDrink;
    }

    public void addDrink(Drink drink) {
        // Post to database
        mRepository.postDrink(drink);

        // Update mDrinks
        mDrinks = mRepository.getDrinks(() -> mIsUpdating.setValue(false));
    }
}
