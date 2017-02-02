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
public class B01Client extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_USER_B01,new B01Client());
    }
    public B01Client() {
        super();
    }
    public B01Client(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new B01Client(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }

}
