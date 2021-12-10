package com.illusion_softworks.kjoerbar.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.illusion_softworks.kjoerbar.R;

import java.util.HashMap;
import java.util.Map;

public class Notifications {
    public static final int SESSION_COMPLETE = 1;

    private static final String CHANNEL_ID = "channel_id";
    private static Context context;
    private static final Map<Integer, NotificationCompat.Builder> notificationMap = new HashMap<>();

    public static void init(Context ctx) {
        context = ctx;
        setNotificationChannels();
        setNotifications();
    }

    // Used elsewhere in the app to show a notification
    public static void showNotification(int notificationId) {
        NotificationCompat.Builder builder = notificationMap.get(notificationId);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }

    private static void setNotificationChannels() {
        // Set Notification channels
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Name", importance);
        channel.setDescription("Description");

        // Register the channel with the system
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private static void setNotifications() {
        // Put notifications in map
        notificationMap.put(SESSION_COMPLETE, new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.beverage_item_bg)
                .setContentTitle("Du er edru!")
                .setContentText("Nå er du i stand til å kjøre bil")
                .setPriority(NotificationCompat.PRIORITY_HIGH));
    }
}