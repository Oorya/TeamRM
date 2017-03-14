package com.teamrm.teamrm.Broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UtlAlarmManager;

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
         /*if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
         {
                Log.d("MESSEGE", "BootReceiver is activate after booting");
             utlAlarmManager = new UtlAlarmManager(context);
             UtlFirebase.getAllTickets(this);

         }
         else
         {
             ticketId = intent.getStringExtra("ticketID");
             alamId = intent.getIntExtra("alarmId",5);
             UtlFirebase.getTicketByKey(ticketId,this);


             Log.d("MESSEGE", "BootReceiver is activate wen alarm start ");

         }*/
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            Toast.makeText(context, "START RECEIVER TEAM_RM", Toast.LENGTH_SHORT).show();
            Log.w("Receiver ","Start boot receiver");

            Intent service = new Intent(context, FirebaseBackgroundService.class);
            context.startService(service);
        }

    }

    @Override
    public void resultTicket(Ticket ticket) {
        /*this.ticket = ticket;
        handelAlam();*/
    }

    @Override
    public void resultUser(Users user) {

    }


    @Override
    public void ticketListCallback(List<Ticket> ticket) {

    }

    public void handelAlam()
    {
        /*switch (alamId)
        {
            case TicketStateAble.TTL_SEND_DATE:
            {
                UtlNotification utlNotification = new UtlNotification("הלקוח מחקה לקביעת מועד","יום נפלא");
                utlNotification.sendNotification();
                break;
            }
            case TicketStateAble.WAITING_FOR_TECH_APPROVAL:
            {
                if(UserSingleton.getInstance().getUserStatus().equals(Users.STATUS_ADMIN)) {
                    UtlNotification utlNotification = new UtlNotification("התכנאי עדיין לא אשר מועד", "יום נפלא");
                    utlNotification.sendNotification();
                }
                break;
            }
            case TicketStateAble.WAITING_FOR_USER_APPROVAL:
            {
                if (ticket.getRepeatSendCounter()>=3)
                {
                    ticket.updateTicketStateString(TicketStateStringable.STATE_E02,ticket);
                    ticket.incInitialization();
                }else
                {
                    ticket.incCounter();
                    ticket.updateTicketStateString(TicketStateStringable.STATE_A02CN,ticket);
                }
                break;
            }
            case TicketStateAble.TTL_END_TICKET_DATE:
            {
                if(ticket.getTicketStateString() != TicketStateStringable.STATE_B03) {
                    if (UserSingleton.getInstance().getUserStatus().equals(Users.STATUS_ADMIN) ) {
                        UtlNotification utlNotification = new UtlNotification("תקלה לא תופלה", "יום נפלא");
                        utlNotification.sendNotification();
                        ticket.updateTicketStateString(TicketStateStringable.STATE_E02,ticket);
                        ticket.incInitialization();
                    }
                }else
                {

                }
                break;
            }
            case TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION:
            {
                ticket.updateTicketStateString(TicketStateStringable.STATE_E05,ticket);
            }
            case TicketStateAble.TECH_START_WORK_ON_TICkET:
            {
                ticket.updateTicketStateString(TicketStateStringable.STATE_E03,ticket);
            }
        }*/
    }


    @Override
    public void resultBoolean(boolean bool) {

    }


    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {

    }

    @Override
    public void companyListCallback(List<Company> companies) {

    }

    @Override
    public void productListCallback(List<Product> products) {

    }

    @Override
    public void categoryListCallback(List<Category> categories) {

    }

    @Override
    public void regionListCallback(List<Region> regions) {

    }
}
