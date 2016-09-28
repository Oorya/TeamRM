package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class A03Admin extends TicketStateAdmin implements TicketStateAble {


    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new A03Admin());
    }
    public A03Admin() {
        super();
    }
    public A03Admin(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new A03Admin();
    }
}
