package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.referencehandler.BeverageCatalogCollectionReferenceHandler;

import java.util.ArrayList;

public class BeverageCatalogDataHandler {
    private static final CollectionReference beverageCatalogReference = BeverageCatalogCollectionReferenceHandler.getUserDocumentReferenceFromFirestore();
    private static final ArrayList<Beverage> beverages = new ArrayList<>();

    public static void getAlcoholUnitCatalog() {
        beverageCatalogReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getalcounitcatalog", String.valueOf(document.getData()));
                    beverages.add(document.toObject(Beverage.class));
                }
            } else {
                Log.w("DATAHANDLER", "Error getting document", task.getException());
            }
        });
    }

    public static void addBeverageToCatalog(@NonNull ArrayList<Beverage> beverages) {
        for (Beverage beverage : beverages) {
            addBeverageToCatalog(beverage);
        }
    }

    public static void addBeverageToCatalog(@NonNull Beverage beverage) {
        beverageCatalogReference.document(beverage.getName())
                .set(beverage)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully added!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static ArrayList<Beverage> getBeverages() {
        return beverages;
    }
}
