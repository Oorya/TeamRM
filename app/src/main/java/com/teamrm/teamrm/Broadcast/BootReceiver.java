package com.teamrm.teamrm.Broadcast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Fragment.CalendarView;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlFirebase;
import com.teamrm.teamrm.Utility.UtlNotification;

import java.util.Calendar;
import java.util.List;

/**
 * Created by shalty on 30/08/2016.
 */
public class BootReceiver extends WakefulBroadcastReceiver implements FireBaseAble {


    List<Ticket> tickets;
    Context context;
    UtlAlarmManager utlAlarmManager;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
         if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Log.d("MESSEGE", "BootReceiver is activate after booting");
             utlAlarmManager = new UtlAlarmManager(context);
             UtlFirebase.getAllTicket();

            } else {
                Log.d("MESSEGE", "BootReceiver is activate wen alarm start ");
             UtlNotification utlNotification = new UtlNotification("קיימים עדקונים חדשים","יום נפלא");
             utlNotification.sendNotification();

            }


    }


    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultList(List<Ticket> ticket) {


        Calendar cal = Calendar.getInstance();

        for (int i=0;i<= ticket.size() ;i++)
        {
            if(!(ticket.get(i).endTime==null && ticket.get(i).ttl==null))
            {
                if(ticket.get(i).endTime!=null)
                {
                    if (ticket.get(i).endTime.getTime() - cal.getTime().getTime() < 0)
                    {

                        sendNotification();
                    }else
                    {
                        utlAlarmManager.setAlarm(ticket.get(i).endTime,"endTime");
                    }
                }
                if(ticket.get(i).ttl != null)
                {
                    if(ticket.get(i).ttl.getTime() - cal.getTime().getTime() < 0)
                    {
                        sendNotification();
                    }else
                    {
                        utlAlarmManager.setAlarm(ticket.get(i).ttl,"ttl");
                    }
                }

            }
        }
    }


    @Override
    public void resultBoolean(boolean bool) {

    }
    private void sendNotification()
    {
        //send a notification eventTime/ttl/wait for tech.
        // pass deadline notification start activities display all pass deadline  event

        //CREATE INTENT AND MSG ICON FOR NOTIFICATION
        Intent intent = new Intent(HomeScreen.context, HomeScreen.class);
        UtlNotification notification = new UtlNotification(R.drawable.new_msg_icon, "Status changed", " status", intent);
        notification.sendNotification();
    }
}
