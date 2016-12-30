package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlAlarmManager;
import com.teamrm.teamrm.Utility.UtlNotification;

import java.util.Calendar;
import java.util.Date;

import static com.teamrm.teamrm.Activities.HomeScreen.context;

/**
 * Created by root on 01/09/2016.
 */
public class B02Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_B02,new B02Admin());
    }
    public B02Admin() {
        super();
    }
    public B02Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("טכנאי אישר מועד","יום נפלא");
        utlNotification.sendNotification();

       // UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        //utlAlarmManager.setAlarm(cal.getTime(),TicketStateAble.TTL_END_TICKET_DATE);
    }

    @Override
    public TicketStateAble getNewState() {
        return new B02Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
