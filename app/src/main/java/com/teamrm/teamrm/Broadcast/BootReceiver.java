package com.teamrm.teamrm.Broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.teamrm.teamrm.Fragment.CalendarVeiw;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
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

        this.context=context;

        if (intent != null) {
            if(intent.getAction() != null){
                Log.d("MESSEGE", "Intent: " + intent.getAction());

                Log.d("MESSEGE", "Intent: !null, Action: null");
                Intent intentt =new Intent(context,CalendarVeiw.class);
                UtlNotification notification = new UtlNotification(R.drawable.icon, "Status changed",  " status", intentt,context);
                notification.sendNotification();
                
            }else{
                Log.d("MESSEGE", "Intent: !null, Action: null");
                Intent intentt =new Intent(context,CalendarVeiw.class);
                UtlNotification notification = new UtlNotification(R.drawable.icon, "Status changed",  " status", intentt,context);
                notification.sendNotification();
            }
        }else{
            Log.d("MESSEGE", "Intent: null");
        }

       /*
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                Log.d("MESSEGE", "BootReceiver is activate after booting");

                tickets = UtlFirebase.getAllTicket();

            } else {
                Log.d("MESSEGE", "BootReceiver is activate wen alarm start ");

            }

      */
    }


    @Override
    public void result(Ticket ticket) {

    }

    @Override
    public void resultList(List<Ticket> ticket) {


        Calendar cal = Calendar.getInstance();

        for (Ticket tickets : ticket)
        {
            if(tickets.endTime.getTime()-cal.getTime().getTime()<0)
            {
                //send a notification eventTime/ttl/wait for tech.
                // pass deadline notification start activities display all pass deadline  event

                //CREATE INTENT AND MSG ICON FOR NOTIFICATION
                //Intent intent=new Intent(MainActivity.context,TicketList.class);
                //UtlNotification notification = new UtlNotification(R.drawable.new_msg_icon, "Status changed",  " status", intent, MainActivity.context);
                //notification.sendNotification();
            }else
            {
                // utlAlarmManager = new UtlAlarmManager(context,)
            }
        }
    }


}
