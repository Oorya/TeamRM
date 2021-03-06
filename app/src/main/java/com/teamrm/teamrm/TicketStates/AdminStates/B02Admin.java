package com.teamrm.teamrm.TicketStates.AdminStates;

import android.content.Context;
import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by root on 01/09/2016.
 */
public class B02Admin extends TicketStateAdmin implements TicketStateAble {
    private Context context = App.getInstance().getApplicationContext();
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_B02,new B02Admin());
    }
    public B02Admin() {
        super();
    }
    public B02Admin(Ticket ticket)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("טכנאי אישר מועד","יום נפלא");
        utlNotification.sendNotification();

        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.cancelAlarm(ticket.get_alarm());

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date date=null;
        try {
            date = dateFormat.parse(ticket.getTicketCloseDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ticket.setAlarm(utlAlarmManager.setAlarm(date,TicketStateAble.TTL_END_TICKET_DATE,ticket.getTicketID()));
        ticket.setAlarmID(TicketStateAble.TTL_END_TICKET_DATE);
        ticket.setAlarm(utlAlarmManager.setAlarm(date,TicketStateAble.TECH_START_WORK_ON_TICkET,ticket.getTicketID()));
        ticket.setAlarmID(TicketStateAble.TECH_START_WORK_ON_TICkET);

    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new B02Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
