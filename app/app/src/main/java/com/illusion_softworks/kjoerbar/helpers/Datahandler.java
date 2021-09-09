package com.illusion_softworks.kjoerbar.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.illusion_softworks.kjoerbar.model.User;

public class Datahandler {

    public static void populateData() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
        CollectionReference userCollectionReference = firestoreDb.collection("users");

        assert firebaseUser != null;
        Log.d("DATAHANDLER", firebaseUser.getUid());
        User user = new User(firebaseUser.getUid(), 100, 180, 20, "Male");
        Log.d("DATAHANDLER", user.toString());
        addToFirestore(firebaseUser, userCollectionReference, user);
    }

    private static void addToFirestore(FirebaseUser firebaseUser, CollectionReference userCollectionReference, User user) {
        userCollectionReference.document(firebaseUser.getUid()).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DATAHANDLER", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DATAHANDLER", "Error writing document", e);
                    }
                });
    }
}


