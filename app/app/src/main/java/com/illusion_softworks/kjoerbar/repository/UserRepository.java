package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.interfaces.ICallBack;
import com.illusion_softworks.kjoerbar.model.User;

// @TODO: Unfinished

/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class UserRepository {
    private static UserRepository sInstance;
    private final MutableLiveData<User> mDataSet = new MutableLiveData<>();
    private static User user;
    private static DocumentReference userDocumentReference;


    public static UserRepository getInstance() {
        if (sInstance == null) {
            sInstance = new UserRepository();
        }
        return sInstance;
    }

    public static void updateUserDocumentReference() {
        userDocumentReference = FirestoreHandler.getUserDocumentReference();
    }

    public MutableLiveData<User> getUser(ICallBack callback) {
        // Database queries
        user = new User();
        userDocumentReference = FirestoreHandler.getUserDocumentReference();
        userDocumentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                final DocumentSnapshot document = task.getResult();
                System.out.println(document);
                if (document.exists()) {
                    Log.d("DATAHANDLER", "DocumentSnapshot data: " + document.getData());
                    user = document.toObject(User.class);
                    assert user != null;
                    Log.d("Current user user datahandler", user.toString());
                } else {
                    Log.d("DATAHANDLER", "No such document");
                }
                mDataSet.setValue(user);
                callback.call();
                Log.d("DATAHANDLER", "get failed with ", task.getException());
            }
        });
        return mDataSet;
    }
}
