package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlNotification;
import static com.teamrm.teamrm.Activities.HomeScreen.context;

/**
 * Created by root on 01/09/2016.
 */
public class E02Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_E02,new E02Admin());
    }
    public E02Admin() {
        super();
    }
    public E02Admin(Ticket ticket)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("דרוש תאום ישיר מול לקוח","יום נפלא");
        utlNotification.sendNotification();
        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.cancelAlarm(ticket.get_alarm());
        ticket.setAlarmID(0);
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E02Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
