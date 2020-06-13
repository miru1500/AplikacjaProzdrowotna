package com.example.aplikacjaprozdrowotna;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.aplikacjaprozdrowotna.R;

public class AchievmentBroadcast10Cigs extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyTest")
                .setSmallIcon(R.drawable.happy)
                .setContentTitle("To już bez 10 papierosa!")
                .setContentText("Brawo, nie spaliłeś już 10 papierosów! Tak trzymaj!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
