package com.illusion_softworks.kjoerbar.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.repository.UserRepository;

import java.util.List;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> mUser;
    private MutableLiveData<List<Drink>> mDrinks;
    private final MutableLiveData<Boolean> mIsUpdating = new MutableLiveData<>();

    public void init() {
        mIsUpdating.setValue(true);
        UserRepository mRepository = UserRepository.getInstance();
        mUser = mRepository.getUser(() -> mIsUpdating.setValue(false));
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public LiveData<Boolean> getIsUpdating() {
        return mIsUpdating;
    }
}
