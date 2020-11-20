package com.example.macros.ui.Reminder;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "CHANNEL_SAMPLE";


    @Override
    public void onReceive(Context context, Intent inte) {

        // Get id & message
        int notificationId = inte.getIntExtra("notificationId", 0);
        String message = inte.getStringExtra("message");

        // Call ReminderFragment when notification is tapped.
        Intent mainIntent = new Intent(context, ReminderFragment.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

        // NotificationManager
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API 26 and above
            CharSequence channelName = "My Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        // Prepare Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle("Daily Reminder :)")
                .setContentText(mainIntent.getStringExtra("message"))
                .setContentIntent(contentIntent)
                .setStyle(new NotificationCompat.InboxStyle()
                .addLine("Eat healthy")
                .addLine("Eat 30 grams of proteins")
                .addLine("Eat 50 grams of Carbs")
                                .addLine("Eat 20 grams of Fat")
                )

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false);


        // Notify
        notificationManager.notify(notificationId, builder.build());
    }
}
