package com.illusion_softworks.kjoerbar.helpers;

import android.util.Log;

import com.illusion_softworks.kjoerbar.datahandler.UserDatahandler;
import com.illusion_softworks.kjoerbar.model.User;

public class DummyData {
    public static void populateData() {
        User user = new User(100, 180, 20, "Male", "Name");
        Log.d("DATAHANDLER", user.toString());
        UserDatahandler.addUserToFirestore(user);
    }
}
