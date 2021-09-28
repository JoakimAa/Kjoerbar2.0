package com.illusion_softworks.kjoerbar.referencehandler;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseReferenceHandler {

    public static FirebaseFirestore getFirebaseReference() {
        return FirebaseFirestore.getInstance();
    }
}
