package com.illusion_softworks.kjoerbar.handler;

// @TODO: REMOVE THIS CLASS

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Drink;

import java.util.ArrayList;

public class BeverageCatalogDataHandler {
    private static final CollectionReference beverageCatalogReference = FirestoreHandler.getDrinkCollectionReference();
    private static final ArrayList<Drink> drinks = new ArrayList<>();

    public static void getAlcoholUnitCatalog() {
        beverageCatalogReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getalcounitcatalog", String.valueOf(document.getData()));
                    drinks.add(document.toObject(Drink.class));
                }
            } else {
                Log.w("DATAHANDLER", "Error getting document", task.getException());
            }
        });
    }

    public static void addBeverageToCatalog(@NonNull ArrayList<Drink> drinks) {
        for (Drink drink : drinks) {
            addBeverageToCatalog(drink);
        }
    }


    public static void addAlcoholUnitToCatalog() {
        Drink grevensPaere = new Drink("Low Vin", "Vin", "Vin", 1, 0.001);
        addBeverageToCatalog(grevensPaere);
        /*
        UserDataHandler.addAlcoholUnitToCatalog(grevensPaere);
        UserDataHandler.addAlcoholUnitToCatalog(grevensEple);
        UserDataHandler.addAlcoholUnitToCatalog(grevensIsteOgFersken);
        UserDataHandler.addAlcoholUnitToCatalog(tuborg);
        UserDataHandler.addAlcoholUnitToCatalog(heineken);
        UserDataHandler.addAlcoholUnitToCatalog(absolute);
        */

      /*  AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(grevensPaere);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(grevensEple);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(grevensIsteOgFersken);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(tuborg);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(heineken);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(absolute);*/
    }

    public static void addBeverageToCatalog(@NonNull Drink drink) {
        beverageCatalogReference.document(drink.getName())
                .set(drink)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "Beverage successfully added to the catalog!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static ArrayList<Drink> getBeverages() {
        return drinks;
    }
}
