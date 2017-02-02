package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlAlarmManager;

import java.util.Calendar;
import java.util.Date;

import static com.teamrm.teamrm.Activities.HomeScreen.context;

/**
 * Created by root on 01/09/2016.
 */
public class A03Admin extends TicketStateAdmin implements TicketStateAble {


    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_A03,new A03Admin());
    }
    public A03Admin() {
        super();
    }
    public A03Admin(Ticket ticket)
    {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar ticketOpenDateTime/date
        cal.add(Calendar.MINUTE, 1); // adds 5 hours
        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.cancelAlarm(ticket.get_alarm());
        ticket.setAlarmID(0);
       ticket.setAlarm(utlAlarmManager.setAlarm(ticket.getTicketCloseDateTime(),TicketStateAble.WAITING_FOR_USER_APPROVAL,ticket.getTicketID()));
        ticket.setAlarmID(TicketStateAble.WAITING_FOR_USER_APPROVAL);


    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new A03Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
