package com.teamrm.teamrm.Utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.SplashScreen;
import com.teamrm.teamrm.R;

public class UtlNotification {

    private Context nContext = App.getInstance().getApplicationContext();
    private static int notificationCounter = 0;
    private int notificationID;
    private int icon;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private CharSequence title;
    private String text;
    private Intent intent;
    private PendingIntent resultPendingIntent;

    public UtlNotification() {
    }

    public UtlNotification(CharSequence title, String text, boolean isAdmin) {
        Intent enrollmentFragment = new Intent(nContext, SplashScreen.class);
        enrollmentFragment.putExtra("enrollmentFrag", true);

        notificationID = ++notificationCounter;
        this.icon = R.drawable.ic_logo_white;
        this.title = title;
        this.text = text;

        resultPendingIntent = PendingIntent.getActivity(nContext, 0, enrollmentFragment, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public UtlNotification(int icon, CharSequence title, String text) {
        Intent homeScreen = new Intent(nContext, HomeScreen.class);
        notificationID = ++notificationCounter;
        this.icon = icon;
        this.title = title;
        this.text = text;

        resultPendingIntent = PendingIntent.getActivity(nContext, 0, homeScreen, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public UtlNotification(CharSequence title, String text) {
        Intent homeScreen = new Intent(nContext, SplashScreen.class);

        notificationID = ++notificationCounter;
        this.icon = R.drawable.ic_logo_white;
        this.title = title;
        this.text = text;
        resultPendingIntent = PendingIntent.getActivity(nContext, 0, homeScreen, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void sendNotification() {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(nContext)
                .setColor(ContextCompat.getColor(nContext, R.color.colorAccent))
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setSound(alarmSound)
                .setContentIntent(resultPendingIntent);

        notificationManager = (NotificationManager) nContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(001, builder.build());
    }
}