package com.illusion_softworks.kjoerbar.viewmodel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.repository.DrinkCatalogRepository;

import java.util.List;

public class DrinkCatalogViewModel extends ViewModel {
    private MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();
    private MutableLiveData<List<Drink>> mDrinks;
    private DrinkCatalogRepository mRepository;

    public void init() {
        if (mDrinks != null) {
            return;
        }
        mIsUpdating.setValue(true);
        mRepository = DrinkCatalogRepository.getInstance();
        mDrinks = mRepository.getDrinks(() -> {
            mIsUpdating.setValue(false);
            Log.d("ViewModel", "CALLBACK HAS BEEN CALLED.... BACK... YAY!");
        });
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
