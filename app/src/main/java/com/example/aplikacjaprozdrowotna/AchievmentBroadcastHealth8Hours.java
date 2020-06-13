package com.example.aplikacjaprozdrowotna;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.aplikacjaprozdrowotna.R;

public class AchievmentBroadcastHealth8Hours extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyTest")
                .setSmallIcon(R.drawable.happy)
                .setContentTitle("Odzyskałeś 8 godzin zdrowia!")
                .setContentText("Brawo, krążenie twojej krwi się poprawiło! Tak trzymaj!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
