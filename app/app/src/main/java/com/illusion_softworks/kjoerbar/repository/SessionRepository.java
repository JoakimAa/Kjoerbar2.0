package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.model.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class SessionRepository {
    private static SessionRepository sInstance;
    private final ArrayList<Session> mDataSet = new ArrayList<>();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final String SESSION_HISTORY = "sessionHistory";
    private static final DocumentReference userDocumentReference = FirestoreHandler.getUserDocumentReference();

    public static SessionRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SessionRepository();
        }
        return sInstance;
    }

    public List<Session> getSessions() {
        // Database queries
        getUserSessions();
        return mDataSet;
    }

    private void getUserSessions() {
        userDocumentReference.collection(SESSION_HISTORY).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getSessionHistory", String.valueOf(document.getData()));
                    mDataSet.add(document.toObject(Session.class));
                }
            } else {
                Log.w("DATAHANDLER", "Error getting user", task.getException());
            }
        });
    }
}
