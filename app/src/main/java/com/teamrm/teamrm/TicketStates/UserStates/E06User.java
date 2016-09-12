package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.ProductID1111;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class E06User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID1111.TICKET_ID_A00T,new E06User());
    }
    public E06User() {
        super();
    }
    public E06User(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new E06User();
    }

}
