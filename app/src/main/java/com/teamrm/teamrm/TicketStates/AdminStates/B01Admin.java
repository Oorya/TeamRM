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
public class B01Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_B01,new B01Admin());
    }
    public B01Admin() {
        super();
    }
    public B01Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("לקוח אישר מועד","יום נפלא");
        utlNotification.sendNotification();
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar startTime/date
        cal.add(Calendar.HOUR_OF_DAY, 6); // adds 5 hours

        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.setAlarm(cal.getTime(),TicketStateAble.WAITING_FOR_TECH_APPROVAL);
    }

    @Override
    public TicketStateAble getNewState() {
        return new B01Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
