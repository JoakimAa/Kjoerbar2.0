package com.illusion_softworks.kjoerbar.helpers;


import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.illusion_softworks.kjoerbar.datahandler.AlcoholUnitCatalogDataHandler;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.AlcoholUnit;
import com.illusion_softworks.kjoerbar.model.Session;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;

public class DummyData {
    private static final DocumentReference userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();


    public static void populateData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        User user = new User(100, 180, 20, "Male", firebaseUser.getDisplayName());
        Log.d("DATAHANDLER", user.toString());
        UserDataHandler.addUserToFirestore(user);
    }

    public static void addSessionToHistory() {
        userDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User newUser = documentSnapshot.toObject(User.class);
                Log.d("USER", newUser.toString());
                Session session = new Session(newUser.getWeight(), newUser.getGender());
                UserDataHandler.addSessionToHistory(session);
            }
        });
    }

    public static void addAlcoholUnitToCatalog() {
        AlcoholUnit grevensPaere = new AlcoholUnit("Grevens Pære","Hansa", "Cider", "dl", 5,4.7, 18.0);
        AlcoholUnit grevensEple = new AlcoholUnit("Grevens Eple","Hansa", "Cider", "dl", 5, 4.7,18.0);
        AlcoholUnit grevensIsteOgFersken= new AlcoholUnit("Grevens Iste og Fersken","Hansa", "Cider", "dl", 5, 4.7, 18.0);
        AlcoholUnit tuborg = new AlcoholUnit("Tuborg", "Tuborg", "Beer", "dl", 5, 4.7, 18.0);
        AlcoholUnit heineken = new AlcoholUnit("Heineken", "Heineken", "Beer", "dl", 5, 4.7, 18.0);
        AlcoholUnit absolute = new AlcoholUnit("Absolute", "Absolute", "Vodka", "cl", 4, 40.0, 12.6);

        /*
        UserDataHandler.addAlcoholUnitToCatalog(grevensPaere);
        UserDataHandler.addAlcoholUnitToCatalog(grevensEple);
        UserDataHandler.addAlcoholUnitToCatalog(grevensIsteOgFersken);
        UserDataHandler.addAlcoholUnitToCatalog(tuborg);
        UserDataHandler.addAlcoholUnitToCatalog(heineken);
        UserDataHandler.addAlcoholUnitToCatalog(absolute);
        */

        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(grevensPaere);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(grevensEple);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(grevensIsteOgFersken);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(tuborg);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(heineken);
        AlcoholUnitCatalogDataHandler.addAlcoholUnitToCatalog(absolute);
    }

    public static void addDataToFirestore() {
        populateData();
        addSessionToHistory();
        addAlcoholUnitToCatalog();
    }
}
