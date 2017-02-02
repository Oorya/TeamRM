package com.teamrm.teamrm.TicketStates.ClientStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E00Client extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_USER_E00,new E00Client());
    }
    public E00Client() {
        super();
    }
    public E00Client(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("קריאה בוטלה","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E00Client(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }

}
