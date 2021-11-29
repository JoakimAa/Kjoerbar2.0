package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class DrinkCatalogRepository {
    private static DrinkCatalogRepository sInstance;
    private final MutableLiveData<List<Drink>> mDataSet = new MutableLiveData<>();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final CollectionReference drinkCollection = firestore.collection("beverageCatalog");

    public static DrinkCatalogRepository getInstance() {
        if (sInstance == null) {
            sInstance = new DrinkCatalogRepository();
        }
        return sInstance;
    }

    public MutableLiveData<List<Drink>> getDrinks() {
        // Database queries
        getUserDrinks();
//        setDrinksWithDummyData();
        return mDataSet;
    }

    private void getUserDrinks() {
        ArrayList<Drink> drinks = new ArrayList<>();

        drinkCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getUserBeverageCatalog", String.valueOf(document));

                    drinks.add(document.toObject(Drink.class));
                }

                mDataSet.setValue(drinks);
            } else {
                Log.w("DATAHANDLER", "Error getting user", task.getException());
            }
        });
    }

    private void setDrinksWithDummyData() {
        ArrayList<Drink> drinks = new ArrayList<>();

        drinks.add(new Drink("Whiskey", "Rum", "cl", 200, 40));
        drinks.add(new Drink("Wine", "Rum", "cl", 200, 40));
        drinks.add(new Drink("Rum", "Rum", "cl", 200, 40));
        drinks.add(new Drink("Beer", "Rum", "cl", 200, 40));
        drinks.add(new Drink("Bruh", "Rum", "cl", 200, 40));

        mDataSet.setValue(drinks);
    }
}
