package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Broadcast.BootReceiver;

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
    public void setAlarm(Date date ,int alarmId){

        int Id = (int) System.currentTimeMillis();
        myIntent.putExtra("alarmId",alarmId);
        pendingIntent = PendingIntent.getBroadcast(context,Id , myIntent, PendingIntent.FLAG_ONE_SHOT);

        this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.getTime(), this.pendingIntent);
        Log.d("MESSAGE","setAlarm");

    }
    public  void cancelAlarm(UtlAlarmManager alarm) {
        if (alarm!= null) {
            alarm.alarmManager.cancel(alarm.pendingIntent);
            Log.d("MESSAGE","cancelAlarm");

        }
    }
    
    
}
