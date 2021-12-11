package com.illusion_softworks.kjoerbar.utilities;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.illusion_softworks.kjoerbar.R;
import com.illusion_softworks.kjoerbar.activities.MainActivity;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notifications {
    public static final int SESSION_COMPLETE = 1;

    private static final Map<Integer, NotificationCompat.Builder> notificationMap = new HashMap<>();
    private static final String CHANNEL_ID = "channel_id";
    private static Context context;
    private static int smallIcon = R.drawable.beverage_item_bg;

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
        // Shave and a haircut vibrate pattern
        long[] vibrate = new long[]{100, 200, 100, 100, 75, 25, 100, 200, 100, 500, 100, 200, 100, 500};

        // Add notifications to map
        notificationMap.put(SESSION_COMPLETE, new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(smallIcon)
                .setContentTitle("Drikkeøkten er fullført")
                .setContentText("Du er nå edru og i stand til å kjøre bil.")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(vibrate)
                .setContentIntent(createPendingIntent())
                .setAutoCancel(true));
    }

    private static PendingIntent createPendingIntent() {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }
}