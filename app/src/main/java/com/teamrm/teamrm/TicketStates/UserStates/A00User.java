package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class A00User extends TicketStateUser implements TicketStateAble {

    static {
        TicketFactory.registerProduct(ProductID.TICKET_CLASS_NAME_A00A,new A00User());
    }
    public A00User() {
        super();
    }
    public A00User(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new A00User(1);
    }
}
