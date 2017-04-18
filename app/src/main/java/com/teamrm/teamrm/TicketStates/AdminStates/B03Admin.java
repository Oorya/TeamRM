package com.teamrm.teamrm.TicketStates.AdminStates;

import android.content.Context;
import android.view.View;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.App;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class B03Admin extends TicketStateAdmin implements TicketStateAble {
    private Context context = App.getInstance().getApplicationContext();
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_B03,new B03Admin());
    }
    public B03Admin() {
        super();
    }
    public B03Admin(Ticket ticket)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("טכנאי התחיל טיפול","יום נפלא");
        utlNotification.sendNotification();
        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.cancelAlarm(ticket.get_alarmTechStartWorkOnTicket());
        utlAlarmManager.cancelAlarm(ticket.get_alarm());
        ticket.setAlarmID(0);
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new B03Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
