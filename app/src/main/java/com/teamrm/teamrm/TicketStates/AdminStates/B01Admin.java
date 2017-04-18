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

import java.util.Calendar;
import java.util.Date;


/**
 * Created by root on 01/09/2016.
 */
public class B01Admin extends TicketStateAdmin implements TicketStateAble {
    private Context context = App.getInstance().getApplicationContext();
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_B01,new B01Admin());
    }
    public B01Admin() {
        super();
    }
    public B01Admin(Ticket ticket)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("לקוח אישר מועד","יום נפלא");
        utlNotification.sendNotification();
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar ticketOpenDateTime/date
        cal.add(Calendar.HOUR_OF_DAY, 6); // adds 5 hours
        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.cancelAlarm(ticket.get_alarm());
        ticket.setAlarmID(0);
        ticket.setAlarm(utlAlarmManager.setAlarm(cal.getTime(),TicketStateAble.WAITING_FOR_TECH_APPROVAL,ticket.getTicketID()));
        ticket.setAlarmID(TicketStateAble.WAITING_FOR_TECH_APPROVAL);
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new B01Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
