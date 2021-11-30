package com.illusion_softworks.kjoerbar.handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreHandler {
    private static FirebaseUser firebaseUser;

    public static CollectionReference getDrinkCollectionReference() {
        FirebaseFirestore firestoreDb = FirestoreHandler.getFirebaseReference();
        return firestoreDb.collection("beverageCatalog");
    }

    public static FirebaseFirestore getFirebaseReference() {
        return FirebaseFirestore.getInstance();
    }

    public static FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void setFirebaseUser(FirebaseUser firebaseUser) {
        FirestoreHandler.firebaseUser = firebaseUser;
    }

    public static DocumentReference getUserDocumentReference() {
        CollectionReference usersCollection = getFirebaseReference().collection("users");
        return usersCollection.document(getFirebaseUser().getUid());
    }
}
