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
                    Map<String, Object> mapBeverage = document.getData();
                    addBeverageToArray(mapBeverage);
                }
            } else {
                Log.w("DATAHANDLER", "Error getting document", task.getException());
            }
        });
    }

    private static void addBeverageToArray(Map<String, Object> mapBeverage) {
        Beverage beverage = new Beverage(
                Objects.requireNonNull(mapBeverage.get("name")).toString(),
                Objects.requireNonNull(mapBeverage.get("producer")).toString(),
                Objects.requireNonNull(mapBeverage.get("category")).toString(),
                Float.parseFloat(Objects.requireNonNull(mapBeverage.get("amount")).toString()),
                Double.parseDouble(Objects.requireNonNull(mapBeverage.get("percent")).toString()));
        Log.d("DATAHANDLER_ALCO", beverage.getName());
        beverages.add(beverage);
        Log.d("DATAHANDLER_RETURN_LOOP", beverages.toString());
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

    public static ArrayList<Beverage> getAlcoholUnits() {
        return beverages;
    }
}
