package com.teamrm.teamrm.Broadcast;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
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
    Ticket ticket;
    private String ticketId;
    private int alamId;
    UtlAlarmManager utlAlarmManager;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private PendingIntent resultPendingIntent;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        Log.w(" start receiver ","start receiver");
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

        DatabaseReference TICKET_LITE_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference("TicketLites");
        TICKET_LITE_ROOT_REFERENCE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UtlNotification notification = new UtlNotification("ערב טוב","התקבלה הודעה חדשה", context);
                notification.sendNotification();
                Toast.makeText(context, "START RECEIVER", Toast.LENGTH_SHORT).show();
                Log.w("receiver ","boot receiver");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
    public void ticketListCallback(List<Ticket> ticket) {


        Calendar cal = Calendar.getInstance();

        for (int i=0;i<= ticket.size() ;i++)
        {
            if(!(ticket.get(i).getTicketCloseDateTime() ==null && ticket.get(i).getTtl()==null))
            {
                if(ticket.get(i).getTicketCloseDateTime() !=null)
                {
                    if (ticket.get(i).getTicketCloseDateTime().getTime() - cal.getTime().getTime() < 0)
                    {

                        sendNotification();
                    }else
                    {
                        ticket.get(i).setAlarm(utlAlarmManager.setAlarm(ticket.get(i).getTicketCloseDateTime(),ticket.get(i).getAlarmID(),ticket.get(i).getTicketID()));
                    }
                }
                if(ticket.get(i).getTtl() != null)
                {
                    if(ticket.get(i).getTtl().getTime() - cal.getTime().getTime() < 0)
                    {
                        sendNotification();
                    }else
                    {
                        ticket.get(i).setAlarm(utlAlarmManager.setAlarm(ticket.get(i).getTtl(),ticket.get(i).getAlarmID(),ticket.get(i).getTicketID()));
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
        }
    }


    @Override
    public void resultBoolean(boolean bool) {

    }
    private void sendNotification()
    {
        //send a notification eventTime/ttl/wait for techNameString.
        // pass deadline notification start activities display all pass deadline  event

        //CREATE INTENT AND MSG ICON FOR NOTIFICATION
        Intent intent = new Intent(HomeScreen.context, HomeScreen.class);
        UtlNotification notification = new UtlNotification(R.drawable.new_msg_icon, "Status changed", " ticketPresentation", intent);
        notification.sendNotification();
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
