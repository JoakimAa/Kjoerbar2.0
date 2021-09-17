package com.illusion_softworks.kjoerbar.referencehandler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserDocumentReferenceHandler {
    private static final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    public static DocumentReference getUserDocumentReferenceFromFirestore() {
        FirebaseFirestore firestoreDb = FirebaseReferenceHandler.getFirebaseReference();
        CollectionReference usersCollectionReference = firestoreDb.collection("users");
        assert firebaseUser != null;
        return usersCollectionReference.document(firebaseUser.getUid());
    }
}
