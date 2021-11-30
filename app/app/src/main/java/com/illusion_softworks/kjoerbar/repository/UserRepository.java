package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.ArrayList;
import java.util.List;

// @TODO: Unfinished

/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class UserRepository {
    private static DrinkCatalogRepository sInstance;
    private final ArrayList<Drink> mDataSet = new ArrayList<>();
    private final FirebaseAuth mAuthInstance = FirebaseAuth.getInstance();
    private final FirebaseUser user = mAuthInstance.getCurrentUser();

    public static DrinkCatalogRepository getInstance() {
        if (sInstance == null) {
            sInstance = new DrinkCatalogRepository();
        }
        return sInstance;
    }

    public List<Drink> getDrinks() {
        // Database queries
        getUser();
        return mDataSet;
    }

    private void getUser() {
//        userDocumentReference.collection(COLLECTION_USER).get().addOnCompleteListener(task -> {
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

}
