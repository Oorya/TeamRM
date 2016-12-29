package com.teamrm.teamrm.Broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Interfaces.TicketStatus;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
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
    Ticket ticket;
    private String ticketId;
    private int alamId;
    UtlAlarmManager utlAlarmManager;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
         if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
         {
                Log.d("MESSEGE", "BootReceiver is activate after booting");
             utlAlarmManager = new UtlAlarmManager(context);
             UtlFirebase.getAllTicket();

         }
         else
         {
             ticketId = intent.getStringExtra("ticketId");
             alamId = intent.getIntExtra("alarmId",5);
             UtlFirebase.getTicketByKey(ticketId,this);


             Log.d("MESSEGE", "BootReceiver is activate wen alarm start ");

         }


    }


    @Override
    public void resultTicket(Ticket ticket) {
        this.ticket = ticket;
        handelAlam();
    }

    @Override
    public void resultUser(Users user) {

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
                        utlAlarmManager.setAlarm(ticket.get(i).endTime,ticket.get(i).alarmID,ticket.get(i).ticketId);
                    }
                }
                if(ticket.get(i).ttl != null)
                {
                    if(ticket.get(i).ttl.getTime() - cal.getTime().getTime() < 0)
                    {
                        sendNotification();
                    }else
                    {
                        utlAlarmManager.setAlarm(ticket.get(i).ttl,ticket.get(i).alarmID,ticket.get(i).ticketId);
                    }
                }

            }
        }
    }
    public void handelAlam()
    {
        switch (alamId)
        {
            case TicketStateAble.TTL_SEND_DATE:
            {
                UtlNotification utlNotification = new UtlNotification("הלקוח מחקה לקביעת מועד","יום נפלא");
                utlNotification.sendNotification();
                break;
            }
            case TicketStateAble.WAITING_FOR_TECH_APPROVAL:
            {
                if(UserSingleton.getInstance().getStatus()==Users.STATUS_ADMIN) {
                    UtlNotification utlNotification = new UtlNotification("התכנאי עדיין לא אשר מועד", "יום נפלא");
                    utlNotification.sendNotification();
                }
                break;
            }
            case TicketStateAble.WAITING_FOR_USER_APPROVAL:
            {
                if (ticket.getRepeatSendCounter()>3)
                {
                    UtlFirebase.changeState(ticketId, ProductID.STATE_E02);
                    ticket.incInitialization();
                }else
                {
                    ticket.incCounter();
                    UtlFirebase.changeState(ticketId, ProductID.STATE_A02CN);


                }
                break;
            }
            case TicketStateAble.TTL_END_TICKET_DATE:
            {
                if(ticket.state!=ProductID.STATE_B03) {
                    if (UserSingleton.getInstance().getStatus() == Users.STATUS_ADMIN) {
                        UtlNotification utlNotification = new UtlNotification("תקלה לא תופלה", "יום נפלא");
                        utlNotification.sendNotification();
                        UtlFirebase.changeState(ticketId, ProductID.STATE_E02);
                        ticket.incInitialization();
                    }
                }else
                {

                }
                break;
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
