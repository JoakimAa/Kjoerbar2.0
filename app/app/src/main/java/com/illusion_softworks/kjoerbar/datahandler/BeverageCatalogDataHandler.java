package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.referencehandler.BeverageCatalogCollectionReferenceHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class BeverageCatalogDataHandler {
    private static final CollectionReference beverageCatalogReference = BeverageCatalogCollectionReferenceHandler.getUserDocumentReferenceFromFirestore();
    private static ArrayList<Beverage> beverages = new ArrayList<>();

    public static void getAlcoholUnitCatalog() {
        beverageCatalogReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Map<String, Object> mapAlcoholUnit = document.getData();
                    addAlcoholUnitToArray(mapAlcoholUnit);
                }
            } else {
                Log.w("DATAHANDLER", "Error getting document", task.getException());
            }
        });
    }

    private static void addAlcoholUnitToArray(Map<String, Object> mapAlcoholUnit) {
        Beverage beverage = new Beverage(
                Objects.requireNonNull(mapAlcoholUnit.get("name")).toString(),
                Objects.requireNonNull(mapAlcoholUnit.get("producer")).toString(),
                Objects.requireNonNull(mapAlcoholUnit.get("category")).toString(),
                Objects.requireNonNull(mapAlcoholUnit.get("amountType")).toString(),
                Float.parseFloat(Objects.requireNonNull(mapAlcoholUnit.get("amount")).toString()),
                Double.parseDouble(Objects.requireNonNull(mapAlcoholUnit.get("percent")).toString()));
        Log.d("DATAHANDLER_ALCO", beverage.getName());
        beverages.add(beverage);
        Log.d("DATAHANDLER_RETURN_LOOP", beverages.toString());
    }

    public static void addAlcoholUnitsToCatalog(@NonNull ArrayList<Beverage> beverages) {
        for (Beverage beverage : beverages) {
            addAlcoholUnitToCatalog(beverage);
        }
    }

    public static void addAlcoholUnitToCatalog(@NonNull Beverage beverage) {
        beverageCatalogReference.document(beverage.getName())
                .set(beverage)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully added!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static ArrayList<Beverage> getAlcoholUnits() {
        return beverages;
    }
}
