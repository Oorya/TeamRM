package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.renderscript.RenderScript;
import android.util.Log;

import com.teamrm.teamrm.Broadcast.BootReceiver;

import java.sql.Time;
import java.util.Calendar;

/**
 * Created by shalty on 05/09/2016.
 */
public class UtlAlarmManager extends Activity{

    private Context context;
    private  AlarmManager alarmManager ;
    private  PendingIntent pendingIntent;
    private  Intent myIntent;
    private  Activity activity;
    
    public UtlAlarmManager(Context context, Activity activity)
    {
        this.alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        this.myIntent = new Intent(activity, BootReceiver.class);
        this.activity=activity;
        this.context=context;
        
    }
    public void setAlarm(Long calendarTaim){


         int Id = (int) System.currentTimeMillis();
        pendingIntent = PendingIntent.getBroadcast(activity,Id , myIntent, PendingIntent.FLAG_ONE_SHOT);
        this.alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarTaim, this.pendingIntent);
        Log.d("MESSEGE","setAlarm");

    }
    public  void cancelAlarm() {
        if (this.alarmManager!= null) {
            this.alarmManager.cancel(this.pendingIntent);
            Log.d("MESSEGE","cancelAlarm");

        }
    }
    
    
}
