package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class E05User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00T,new E05User());
    }
    public E05User() {
        super();
    }
    public E05User(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new E05User();
    }

}
