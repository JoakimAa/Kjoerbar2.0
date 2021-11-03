package com.illusion_softworks.kjoerbar.datahandler;

// @TODO: REMOVE THIS CLASS

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.illusion_softworks.kjoerbar.model.Drink;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;

import java.util.ArrayList;
import java.util.Map;

public class UserDataHandler {
    private static final String BEVERAGE_CATALOG = "beverageCatalog";
    private static final String SESSION_HISTORY = "sessionHistory";
    private static DocumentReference userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();
    private static User user;
    private static ArrayList<Drink> drinks = new ArrayList<>();
    private static ArrayList<Session> sessions = new ArrayList<>();
    public static void updateUserDocumentReference() {
        UserDataHandler.userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();
    }

    public static void addUserToFirestore(User user) {
        userDocumentReference
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "User successfully written!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error writing user", e));
    }

    public static void removeUserFromFirebase() {
        userDocumentReference
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "User successfully removed!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing user", e));
    }

    public static void updateUserOnFireStore(Map<String, Object> user) {
        for (Map.Entry<String, Object> entry : user.entrySet())
            userDocumentReference
                    .update(entry.getKey(), entry.getValue())
                    .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", String.format("User successfully updated! Key: %s, Value: %s", entry.getKey().toString(), entry.getValue().toString())))
                    .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing user", e));
    }

    public static void addSessionToHistory(@NonNull Session session) {
        userDocumentReference.collection("sessionHistory").document(session.getStartDateTime().toString())
                .set(session)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "Session successfully added to history!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error removing session from history", e));
    }

    public static void addBeverageToCatalog(@NonNull Drink drink) {
        userDocumentReference.collection(BEVERAGE_CATALOG).document(drink.getName())
                .set(drink)
                .addOnSuccessListener(aVoid -> Log.d("DATAHANDLER", "Beverage successfully added to user catalog!"))
                .addOnFailureListener(e -> Log.w("DATAHANDLER", "Error adding beverage", e));
    }

    public static void addBeverageToCatalog(@NonNull ArrayList<Drink> drinks) {
        for (Drink drink : drinks) {
            addBeverageToCatalog(drink);
        }
    }

    public static void getUserBeverageCatalog() {
        userDocumentReference.collection(BEVERAGE_CATALOG).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getUserBeverageCatalog", String.valueOf(document));

                    drinks.add(document.toObject(Drink.class));
                }
            } else {
                Log.w("DATAHANDLER", "Error getting user", task.getException());
            }
        });
    }

    public static void getUserData() {
        updateUserDocumentReference();
        userDocumentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("DATAHANDLER", "DocumentSnapshot data: " + document.getData());

                        if (document.getData() != null) user = document.toObject(User.class);
                        else user = new User();

                        assert user != null;
                        Log.d("Current user user datahandler", user.toString());

                    } else {
                        Log.d("DATAHANDLER", "No such document");
                    }
                } else {
                    Log.d("DATAHANDLER", "get failed with ", task.getException());
                }
            }
        });
    }

    public static void getSessionHistory() {
        userDocumentReference.collection(SESSION_HISTORY).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("DATAHANDLER_getSessionHistory", String.valueOf(document.getData()));
                    sessions.add(document.toObject(Session.class));
                }
            } else {
                Log.w("DATAHANDLER", "Error getting user", task.getException());
            }
        });
    }

    public static ArrayList<Session> getSessions() {
        return new ArrayList<>(sessions);
    }

    public static User getUser() {
        if (user != null)
            Log.d("Current user get", user.toString());
        return user;
    }

    public static void setUser(User user) {
        UserDataHandler.user = user;
    }

    public static ArrayList<Drink> getBeverages() {
        return new ArrayList<>(drinks);
    }
}


