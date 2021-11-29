package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.model.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class SessionHistoryRepository {
    private static SessionHistoryRepository sInstance;
    private static final String TAG = "Session History Repository";
    private final ArrayList<Session> mDataSet = new ArrayList<>();
    private final DocumentReference userRef = FirestoreHandler.getUserDocumentReference();

    public static SessionHistoryRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SessionHistoryRepository();
        }
        return sInstance;
    }

    public List<Session> getSessions() {
        // Database queries
        getData();
        // setDummyData();
        return mDataSet;
    }

    private void getData() {
        // Fetches sessions from collection in firestore for current user

        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {

                    if (document.contains("beverageCatalog")) {
                        // adds session to mDataSet if user contains the collection
                        mDataSet.add((Session) document.get("beverageCatalog"));
                    }

                    // TEST
                    if (document.contains("weight")) {
                        Log.d(TAG, "Weight!! ");
                        Log.d(TAG, "Heyo " + (Long) document.get("weight"));
                    }

                    Log.d(TAG, "Data loaded: " + document.getData());
                } else {
                    Log.d(TAG, "Document not found.");
                }
            } else {
                Log.w(TAG, "Error getting user", task.getException());
            }
        });
    }

    private void postSessions() {

    }

    private void setDummyData() {
        mDataSet.add(new Session(153, "Male"));
        mDataSet.add(new Session(156, "Female"));
        mDataSet.add(new Session(153, "Male"));
        mDataSet.add(new Session(123, "Female"));
        mDataSet.add(new Session(100, "Helicopter"));
        mDataSet.add(new Session(2, "Male"));

    }
}
