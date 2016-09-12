package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.ProductID1111;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class B03User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID1111.TICKET_ID_A00T,new B03User());
    }
    public B03User() {
        super();
    }
    public B03User(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new B03User();
    }

}
