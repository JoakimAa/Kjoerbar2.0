package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.referencehandler.AlcoholUnitCatalogCollectionReferenceHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AlcoholUnitCatalogDataHandler {
    private static final CollectionReference alcoholUnitCatalogReference = AlcoholUnitCatalogCollectionReferenceHandler.getUserDocumentReferenceFromFirestore();
    private static ArrayList<AlcoholUnit> alcoholUnits = new ArrayList<>();

    public static void getAlcoholUnitCatalog() {
        alcoholUnitCatalogReference.get().addOnCompleteListener(task -> {
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
        AlcoholUnit alcoholUnit = new AlcoholUnit(
                Objects.requireNonNull(mapAlcoholUnit.get("name")).toString(),
                Objects.requireNonNull(mapAlcoholUnit.get("producer")).toString(),
                Objects.requireNonNull(mapAlcoholUnit.get("category")).toString(),
                Objects.requireNonNull(mapAlcoholUnit.get("amountType")).toString(),
                Double.parseDouble(Objects.requireNonNull(mapAlcoholUnit.get("amount")).toString()),
                Double.parseDouble(Objects.requireNonNull(mapAlcoholUnit.get("percent")).toString()),
                Double.parseDouble(Objects.requireNonNull(mapAlcoholUnit.get("gramAlcoholPerUnit")).toString())
        );
        Log.d("DATAHANDLER_ALCO", alcoholUnit.getName().toString());
        alcoholUnits.add(alcoholUnit);
        Log.d("DATAHANDLER_RETURN_LOOP", alcoholUnits.toString());
    }

    public static void addAlcoholUnitsToCatalog(@NonNull ArrayList<AlcoholUnit> alcoholUnits) {
        for (AlcoholUnit alcoholUnit : alcoholUnits) {
                addAlcoholUnitToCatalog(alcoholUnit);
        }
    }

    public static void addAlcoholUnitToCatalog(@NonNull AlcoholUnit alcoholUnit) {
            alcoholUnitCatalogReference.document(alcoholUnit.getName())
                .set(alcoholUnit)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully added!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static ArrayList<AlcoholUnit> getAlcoholUnits() {
        return alcoholUnits;
    }
}
