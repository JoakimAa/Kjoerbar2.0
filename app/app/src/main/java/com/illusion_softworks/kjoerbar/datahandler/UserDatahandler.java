package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;

public class UserDatahandler {
    private static final DocumentReference userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();

    public static void addUserToFirestore(User user) {
        userDocumentReference.set(user)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error writing document", e));
    }

    public static void removeUserFromFirebase() {
        userDocumentReference.delete()
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully removed!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));;
    }
}


