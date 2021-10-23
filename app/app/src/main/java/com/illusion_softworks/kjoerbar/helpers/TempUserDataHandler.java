package com.illusion_softworks.kjoerbar.helpers;

import com.illusion_softworks.kjoerbar.model.User;

public class TempUserDataHandler {
    private static User user = new User(100, 90, 20, "Male", "Geir");

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        TempUserDataHandler.user = user;
    }


}
