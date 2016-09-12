package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.ProductID1111;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class B02Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID1111.TICKET_ID_A00A,new B02Admin());
    }
    public B02Admin() {
        super();
    }
    public B02Admin(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new B02Admin();
    }
}
