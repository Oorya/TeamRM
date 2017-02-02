package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Type.Ticket;

/**
 * Created by root on 01/09/2016.
 */
public class C02Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_ADMIN_C02,new C02Admin());
    }
    public C02Admin() {
        super();
    }
    public C02Admin(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new C02Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
