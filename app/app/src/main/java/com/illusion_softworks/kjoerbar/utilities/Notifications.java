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
    public static final String CHANNEL_ID = "channel_id";
    private static final Map<Integer, NotificationCompat.Builder> notificationMap = new HashMap<>();

    public static void init(Context context) {
        setNotificationChannels(context);
        setNotifications(context);
    }

    public static void setNotificationChannels(Context context) {
        // Set Notification channels
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Name", importance);
        channel.setDescription("Description");

        // Register the channel with the system
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public static void setNotifications(Context context) {
        // Put notifications in map
        notificationMap.put(1, new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.beverage_item_bg)
                .setContentTitle("Title")
                .setContentText("Bruh bruh")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT));
    }

    public static void showNotification(Context context, int notificationId) {
        NotificationCompat.Builder builder = notificationMap.get(notificationId);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
    }
}