package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 01/09/2016.
 */
public class E04Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_E04,new E04Admin());
    }
    public E04Admin() {
        super();
    }
    public E04Admin(Ticket ticket)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("טיפול נדחה ברבע שעה","יום נפלא");
        utlNotification.sendNotification();
        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(HomeScreen.context);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date date=null;
        try {
            date = dateFormat.parse(ticket.getTicketCloseDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date.setTime(date.getTime()+900000);
        ticket.setAlarm(utlAlarmManager.setAlarm(date,TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION,ticket.getTicketID()));
        ticket.setAlarmID(TicketStateAble.TTL_END_TIKCET_TIME_EXTENSION);
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E04Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
