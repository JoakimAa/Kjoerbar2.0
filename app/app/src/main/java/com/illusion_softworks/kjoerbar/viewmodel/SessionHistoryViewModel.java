package com.illusion_softworks.kjoerbar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.repository.SessionRepository;

import java.util.List;

public class SessionHistoryViewModel extends ViewModel {
    private MutableLiveData<List<Session>> mSessions;
    private final MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init() {
        SessionRepository mRepository = SessionRepository.getInstance();
        mSessions = new MutableLiveData<List<Session>>();
        mSessions.setValue(mRepository.getSessions());
    }

    public LiveData<List<Session>> getSessions() {
        return mSessions;
    }

    public void addDrinkSimulation(final Drink drink) {
        mIsUpdating.setValue(true);

//        new AsyncTask<Void, Void, Void>(){
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                List<Drink> currentDrinks = mDrinks.getValue();
//                currentDrinks.add(drink);
//                mDrinks.postValue(currentDrinks);
//                mIsUpdating.setValue(false);
//            }
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        }.execute();
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }
}
