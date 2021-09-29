package com.illusion_softworks.kjoerbar.datahandler;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;

import java.util.ArrayList;
import java.util.Map;

public class UserDataHandler {
    private static DocumentReference userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();
    private static User user = new User(0, 0, 0, "", "");

    public static void updateUserDocumentReference() {
        UserDataHandler.userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();
    }

    public static void addUserToFirestore(User user) {
        userDocumentReference
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "User successfully written!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error writing document", e));
    }

    public static void removeUserFromFirebase() {
        userDocumentReference
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully removed!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static void updateUserOnFireStore(Map<String, Object> user) {
        for (Map.Entry<String, Object> entry : user.entrySet())
            userDocumentReference
                    .update(entry.getKey(), entry.getValue())
                    .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "User successfully updated!"))
                    .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static void addSessionToHistory(@NonNull Session session) {
        userDocumentReference.collection("sessionHistory").document(session.getStartDateTime().toString())
                .set(session)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully added!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static void addAlcoholUnitToCatalog(@NonNull Beverage beverage) {
        userDocumentReference.collection("alcoholUnitCatalog").document(beverage.getName())
                .set(beverage)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "DocumentSnapshot successfully added!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing document", e));
    }

    public static void addAlcoholUnitsToCatalog(@NonNull ArrayList<Beverage> beverages) {
        for (Beverage beverage : beverages) {
            addAlcoholUnitToCatalog(beverage);
        }
    }

    public static void getUserData() {
        userDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DATAHANDLER", "DocumentSnapshot data: " + document.getData());
                        user = new User(
                                Integer.parseInt(document.getData().get("weight").toString()),
                                Integer.parseInt(document.getData().get("height").toString()),
                                Integer.parseInt(document.getData().get("age").toString()),
                                document.getData().get("gender").toString(),
                                document.getData().get("username").toString());
                        Log.d("Current user userdatahandler", user.toString());

                    } else {
                        Log.d("DATAHANDLER", "No such document");
                    }
                } else {
                    Log.d("DATAHANDLER", "get failed with ", task.getException());
                }
            }
        });
    }

    public static User getUser() {
        Log.d("Current user get", user.toString());
        return user;
    }
}


