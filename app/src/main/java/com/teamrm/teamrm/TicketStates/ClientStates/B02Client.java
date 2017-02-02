package com.teamrm.teamrm.TicketStates.ClientStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Type.Ticket;

/**
 * Created by root on 01/09/2016.
 */
public class B02Client extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_USER_B02,new B02Client());
    }
    public B02Client() {
        super();
    }
    public B02Client(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new B02Client(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }

}
