package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlAlarmManager;

import java.util.Calendar;
import java.util.Date;

import static com.teamrm.teamrm.Activities.HomeScreen.context;

/**
 * Created by root on 01/09/2016.
 */
public class A02CNAdmin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_A02CN,new A02CNAdmin());
    }
    public A02CNAdmin() {
        super();
    }
    public A02CNAdmin(int ttl)
    {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 5); // adds 5 hours

        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.setAlarm(cal.getTime(),TicketStateAble.TTL_SEND_DATE);
    }

    @Override
    public TicketStateAble getNewState() {
        return new A02CNAdmin(1);
    }
    
    
    
}
