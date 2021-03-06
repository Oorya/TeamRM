package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;

/**
 * Created by root on 01/09/2016.
 */
public class Z00Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_Z00,new Z00Admin());
    }
    public Z00Admin() {
        super();
    }
    public Z00Admin(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new Z00Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
