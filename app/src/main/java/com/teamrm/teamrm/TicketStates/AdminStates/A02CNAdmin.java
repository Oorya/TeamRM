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

import java.util.Calendar;
import java.util.Date;


/**
 * Created by root on 01/09/2016.
 */
public class A02CNAdmin extends TicketStateAdmin implements TicketStateAble {
    private Context context = App.getInstance().getApplicationContext();

    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_A02CN,new A02CNAdmin());
    }
    public A02CNAdmin() {
        super();
    }
    public A02CNAdmin(int ttl)
    {
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar ticketOpenDateTime/date
        cal.add(Calendar.HOUR_OF_DAY, 5); // adds 5 hours

        UtlAlarmManager utlAlarmManager = new UtlAlarmManager(context);
        utlAlarmManager.setAlarm(cal.getTime(),TicketStateAble.TTL_SEND_DATE);
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new A02CNAdmin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }


}
