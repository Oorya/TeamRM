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
public class E03Admin extends TicketStateAdmin implements TicketStateAble {
    private Context context = App.getInstance().getApplicationContext();
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_E03,new E03Admin());
    }
    public E03Admin() {
        super();
    }
    public E03Admin(Ticket ticket)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("זמן הטיפול הגיע, הטכנאי לא התחיל טיפול","יום נפלא");
        utlNotification.sendNotification();
        ticket.setAlarmID(0);
        ticket.setAlarmID(0);
        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.cancelAlarm(ticket.get_alarm());


    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E03Admin(ticket);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
