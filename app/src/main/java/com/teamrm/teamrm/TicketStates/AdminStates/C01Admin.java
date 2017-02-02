package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class C01Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_C01,new C01Admin());
    }
    public C01Admin() {
        super();
    }
    public C01Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("קריאה נסגרה בהצלחה, המתנה לאישור לקוח","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new C01Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
