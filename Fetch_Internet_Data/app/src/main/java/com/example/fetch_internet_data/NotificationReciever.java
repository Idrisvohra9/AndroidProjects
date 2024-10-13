package com.example.fetch_internet_data;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationReciever extends BroadcastReceiver {
    private static final String CHANNEL_ID = "Test";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText("This notification is triggered by Alarm Manager")
                .setContentTitle("Alarm Notification")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        //Show Notification
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            Log.d("In receive", "Let's see");
            notificationManager.notify(1, builder.build());
        }
    }
}
