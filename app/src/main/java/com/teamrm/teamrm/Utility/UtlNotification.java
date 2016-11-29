package com.teamrm.teamrm.Utility;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.MainActivity;
import com.teamrm.teamrm.R;

public class UtlNotification {

    private Context context;
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

    public UtlNotification(int icon, CharSequence title, String text, Intent intent) {
        notificationID = ++notificationCounter;
        this.icon = icon;
        this.title = title;
        this.text = text;
        this.intent = intent;
        this.context = MainActivity.context;

        resultPendingIntent = PendingIntent.getActivity(context, 0, this.intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public UtlNotification(int icon, CharSequence title, String text)
    {
        Intent homeScreen = new Intent(MainActivity.context,HomeScreen.class);
        notificationID=++notificationCounter;
        this.icon=icon;
        this.title=title;
        this.text=text;
        this.context= MainActivity.context;

        resultPendingIntent = PendingIntent.getActivity(context, 0, homeScreen, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public UtlNotification(CharSequence title, String text)
    {
        Intent homeScreen = new Intent(MainActivity.context,HomeScreen.class);

        notificationID=++notificationCounter;
        this.icon= R.drawable.new_msg_icon;
        this.title=title;
        this.text=text;
        this.context= MainActivity.context;

        resultPendingIntent = PendingIntent.getActivity(context, 0, homeScreen, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public void sendNotification() {
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(001, builder.build());
    }
}