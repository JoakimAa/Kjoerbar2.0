package com.illusion_softworks.kjoerbar.helpers;


import android.util.Log;
/*
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.illusion_softworks.kjoerbar.datahandler.BeverageCatalogDataHandler;
import com.illusion_softworks.kjoerbar.datahandler.UserDataHandler;
import com.illusion_softworks.kjoerbar.model.Beverage;
import com.illusion_softworks.kjoerbar.model.User;
import com.illusion_softworks.kjoerbar.referencehandler.UserDocumentReferenceHandler;
*/
import java.util.ArrayList;
import java.util.Arrays;

public class DummyData {
    /*private static final DocumentReference userDocumentReference = UserDocumentReferenceHandler.getUserDocumentReferenceFromFirestore();

    public static void populateData() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        User user = new User(100, 180, 20, "Male", firebaseUser.getDisplayName());
        Log.d("DATAHANDLER", user.toString());
        UserDataHandler.addUserToFirestore(user);
    }

    public static void addBeverageToCatalog() {
        ArrayList<Beverage> beverage = new ArrayList<Beverage>(Arrays.asList(
                new Beverage("Grevens Pære", "Hansa", "Cider", 0.5, 4.7),
                new Beverage("Grevens Eple", "Hansa", "Cider", 0.5, 4.7),
                new Beverage("Grevens Iste og Fersken", "Hansa", "Cider", 0.5, 4.7),
                new Beverage("Tuborg", "Tuborg", "Beer", 0.5, 4.7),
                new Beverage("Heineken", "Heineken", "Beer", 0.5, 4.7),
                new Beverage("Absolute", "Absolute", "Vodka", 0.04, 40.0),
                new Beverage("Red wine", "Red Wine", "Wine", 0.75, 14.0),
                new Beverage("Low per mill", "Low per mill", "Low per mill", 0.01, 1),
                new Beverage("White wine", "Wine", 0.75, 14.0)));

        BeverageCatalogDataHandler.addBeverageToCatalog(beverage);
    }

    public static void addDataToFirestore() {
        populateData();
        addBeverageToCatalog();
    }*/
}
