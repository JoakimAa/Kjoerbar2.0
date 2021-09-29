package com.illusion_softworks.kjoerbar.referencehandler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LocalFirebaseUser {
    private static FirebaseUser firebaseUser;

    public static FirebaseUser getFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void setFirebaseUser(FirebaseUser firebaseUser) {
        LocalFirebaseUser.firebaseUser = firebaseUser;
    }
}
