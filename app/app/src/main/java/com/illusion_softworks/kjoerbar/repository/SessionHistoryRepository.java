package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.interfaces.ICallBack;
import com.illusion_softworks.kjoerbar.model.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class SessionHistoryRepository {
    private static SessionHistoryRepository sInstance;
    private static final String SESSION_HISTORY = "sessionHistory";
    private final MutableLiveData<List<Session>> mDataSet = new MutableLiveData<>();
    private final DocumentReference userDocumentReference = FirestoreHandler.getUserDocumentReference();

    public static SessionHistoryRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SessionHistoryRepository();
        }
        return sInstance;
    }

    public MutableLiveData<List<Session>> getSessions(ICallBack callback) {
        // Database queries
        ArrayList<Session> sessions = new ArrayList<>();
        userDocumentReference.collection(SESSION_HISTORY).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getSessionHistory", String.valueOf(document.getData()));
                    sessions.add(document.toObject(Session.class));
                }
                mDataSet.setValue(sessions);
                callback.call();
            } else {
                Log.w("DATAHANDLER", "Error getting user", task.getException());
            }
        });
        return mDataSet;
    }
}
