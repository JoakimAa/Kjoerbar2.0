package com.illusion_softworks.kjoerbar.viewmodel;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.repository.DrinkCatalogRepository;

import java.util.Arrays;
import java.util.List;

public class DrinkCatalogViewModel extends ViewModel {
    private MutableLiveData<List<Drink>> mDrinks = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private DrinkCatalogRepository mRepository;

    public void init() {
        mRepository = DrinkCatalogRepository.getInstance();
        mDrinks.setValue(mRepository.getDrinks());
    }

    public LiveData<List<Drink>> getDrinks() {
        return mDrinks;
    }

    public void addDrinkSimulation(final Drink drink) {
        mIsUpdating.setValue(true);

        new AsyncTask<Void, Void, Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                List<Drink> currentDrinks = mDrinks.getValue();
                currentDrinks.add(drink);
                mDrinks.postValue(currentDrinks);
                mIsUpdating.setValue(false);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }
}
