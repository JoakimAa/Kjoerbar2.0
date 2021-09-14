package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.illusion_softworks.kjoerbar.model.AlcoholUnitCatalog;
import com.illusion_softworks.kjoerbar.referencehandler.AlcoholUnitCatalogCollectionReference;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;

public class AlcoholUnitCatalogDataHandler {
    private static final CollectionReference alcoholUnitCatalogReference = AlcoholUnitCatalogCollectionReference.getUserDocumentReferenceFromFirestore();

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
