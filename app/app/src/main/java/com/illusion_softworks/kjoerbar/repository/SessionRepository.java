package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Drink;
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
    private final CollectionReference drinkCollection = firestore.collection("beverageCatalog");

    public static SessionRepository getInstance() {
        if (sInstance == null) {
            sInstance = new SessionRepository();
        }
        return sInstance;
    }

    public List<Session> getSessions() {
        // Database queries
        // getUserDrinks();
        setDummyData();
        return mDataSet;
    }

    private void getUserDrinks() {
//        drinkCollection.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                for (QueryDocumentSnapshot document : task.getResult()) {
//                    Log.d("DATAHANDLER_getUserBeverageCatalog", String.valueOf(document));
//
//                    mDataSet.add(document.toObject(Drink.class));
//                }
//            } else {
//                Log.w("DATAHANDLER", "Error getting user", task.getException());
//            }
//        });
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
