package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.AlcoholUnitCatalog;
import com.illusion_softworks.kjoerbar.referencehandler.AlcoholUnitCatalogCollectionReferenceHandler;

public class AlcoholUnitCatalogDataHandler {
    private static final CollectionReference alcoholUnitCatalogReference = AlcoholUnitCatalogCollectionReferenceHandler.getUserDocumentReferenceFromFirestore();

    public static AlcoholUnitCatalog getAlcoholUnitCatalog() {
        alcoholUnitCatalogReference.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("DATAHANDLER", document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w("DATAHANDLER", "Error getting document", task.getException());
                    }
                });

        return new AlcoholUnitCatalog();
    }
}
