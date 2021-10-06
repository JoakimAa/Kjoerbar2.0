package com.illusion_softworks.kjoerbar.referencehandler;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class BeverageCatalogCollectionReferenceHandler {

    public static CollectionReference getUserDocumentReferenceFromFirestore() {
        FirebaseFirestore firestoreDb = FirebaseReferenceHandler.getFirebaseReference();
        return firestoreDb.collection("alcoholUnitCatalog");
    }
}
