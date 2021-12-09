package com.illusion_softworks.kjoerbar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.repository.SessionHistoryRepository;

import java.util.List;

public class SessionHistoryViewModel extends ViewModel {
    private MutableLiveData<List<Session>> mSessions;
    private final MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init() {
       /* if (mSessions != null) {
            return;
        }*/
        mIsUpdating.setValue(true);
        SessionHistoryRepository mRepository = SessionHistoryRepository.getInstance();
        mSessions = mRepository.getSessions(() -> mIsUpdating.setValue(false));
    }

    public LiveData<List<Session>> getSessions() {
        return mSessions;
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }
}
