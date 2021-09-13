package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.illusion_softworks.kjoerbar.model.AlcoholUnitCatalog;

public class AlcoholUnitCatalogDataHandler {

    public static AlcoholUnitCatalog getAlcoholUnitCatalog() {
        FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
        CollectionReference alcoholUnitCatalogReference = firestoreDb.collection("alcoholUnitCatalog");
        alcoholUnitCatalogReference.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("DATAHANDLER", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("DATAHANDLER", "Error getting document", task.getException());
                        }
                    }
                });

        return new AlcoholUnitCatalog();
    }
}
