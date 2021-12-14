package com.illusion_softworks.kjoerbar.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.illusion_softworks.kjoerbar.handler.FirestoreHandler;
import com.illusion_softworks.kjoerbar.handler.UserDataHandler;
import com.illusion_softworks.kjoerbar.interfaces.ICallBack;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.Session;

import java.util.ArrayList;
import java.util.List;


/**
 * Repository with Singleton pattern
 * This is to prevent multiple connections to database at once
 */
public class DrinkCatalogRepository {
    private static DrinkCatalogRepository sInstance;
    private static final String TAG = "Drink_Catalog_Repo";
    private static final String DRINK_CATALOG = "beverageCatalog";
    private final MutableLiveData<List<Drink>> mDataSet = new MutableLiveData<>();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final CollectionReference drinkCollection = firestore.collection("beverageCatalog");
    private final DocumentReference userDocumentReference = FirestoreHandler.getUserDocumentReference();

    public static DrinkCatalogRepository getInstance() {
        if (sInstance == null) {
            sInstance = new DrinkCatalogRepository();
        }
        return sInstance;
    }

    public MutableLiveData<List<Drink>> getDrinks(ICallBack callBack) {
        // Database queries
        ArrayList<Drink> drinks = new ArrayList<>();
        userDocumentReference.collection(DRINK_CATALOG)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("DATAHANDLER_getUserBeverageCatalog", String.valueOf(document));

                            drinks.add(document.toObject(Drink.class));
                        }

                        mDataSet.setValue(drinks);
                        callBack.call();
                    } else {
                        Log.w("DATAHANDLER", "Error getting user", task.getException());
                    }
                });
        return mDataSet;
    }

    public void postDrink(Drink newDrink) {
        // Database queries
        UserDataHandler.addBeverageToCatalog(newDrink);
    }
}
