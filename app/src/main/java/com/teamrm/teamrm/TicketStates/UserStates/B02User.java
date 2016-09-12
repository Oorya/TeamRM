package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class B02User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00T,new B02User());
    }
    public B02User() {
        super();
    }
    public B02User(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new B02User();
    }

}
