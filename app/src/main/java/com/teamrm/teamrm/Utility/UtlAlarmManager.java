package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.teamrm.teamrm.Broadcast.BootReceiver;
import com.teamrm.teamrm.Type.Ticket;

import java.util.Date;

/**
 * Created by shalty on 05/09/2016.
 */
public class UtlAlarmManager extends Activity{

    private Context context;
    private  AlarmManager alarmManager ;
    private  PendingIntent pendingIntent;
    private  Intent myIntent;
    
    public UtlAlarmManager(Context context)
    {
        this.alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        this.myIntent = new Intent(context, BootReceiver.class);
        this.context=context;
    }

    public PendingIntent setAlarm(Date date ,int alarmId,String ticketId){

        int Id = (int) System.currentTimeMillis();
        myIntent.putExtra("alarmId",alarmId);
        myIntent.putExtra("ticketId",ticketId);
        pendingIntent = PendingIntent.getBroadcast(context,Id , myIntent, PendingIntent.FLAG_ONE_SHOT);
        this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        Log.d("MESSAGE","setAlarm");

        //set alarm filed in ticket in firebase
        return pendingIntent;
    }
    public PendingIntent setAlarmFromBrodcast(PendingIntent pendingIntent,Date date ,int alarmId,String ticketId){

        myIntent.putExtra("alarmId",alarmId);
        myIntent.putExtra("ticketId",ticketId);
        this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        Log.d("MESSAGE","setAlarm");

        //set alarm filed in ticket in firebase
        return pendingIntent;
    }
    public void setAlarm(Date date ,int alarmId){

        int Id = (int) System.currentTimeMillis();
        myIntent.putExtra("alarmId",alarmId);
        pendingIntent = PendingIntent.getBroadcast(context,Id , myIntent, PendingIntent.FLAG_ONE_SHOT);

        this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), this.pendingIntent);
        Log.d("MESSAGE","setAlarm");
    }


    public  void cancelAlarm(PendingIntent pi) {

        alarmManager.cancel(pi);

            Log.d("MESSAGE","cancelAlarm");

    }
}
