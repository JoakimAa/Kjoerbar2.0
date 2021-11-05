package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class DrinkCatalogRepository {
    private static DrinkCatalogRepository sInstance;
    private ArrayList<Drink> mDataSet = new ArrayList<>();

    private static final String BEVERAGE_CATALOG = "beverageCatalog";
    private static final DocumentReference userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();


    public static DrinkCatalogRepository getInstance() {
        if (sInstance == null) {
            sInstance = new DrinkCatalogRepository();
        }
        return sInstance;
    }

    public List<Drink> getDrinks() {
        // Database queries

        getUserDrinks();
//        MutableLiveData<List<Drink>> data = new MutableLiveData<>();
//        data.setValue();

        return mDataSet;
    }

    private void getUserDrinks() {
        userDocumentReference.collection(BEVERAGE_CATALOG).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getUserBeverageCatalog", String.valueOf(document));

                    mDataSet.add(document.toObject(Drink.class));
                }
            } else {
                Log.w("DATAHANDLER", "Error getting user", task.getException());
            }
        });
    }

    private void setDrinksWithDummyData() {
        mDataSet.add(new Drink("Whiskey", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Wine", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Rum", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Beer", "Rum", "cl", 200, 40));
        mDataSet.add(new Drink("Bruh", "Rum", "cl", 200, 40));
    }
}
