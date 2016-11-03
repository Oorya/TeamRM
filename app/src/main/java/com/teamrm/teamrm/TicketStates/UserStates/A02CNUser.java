package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Interfaces.ProductID;
/**
 * Created by root on 01/09/2016.
 */
public class A02CNUser extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new A02CNUser());
    }
    public A02CNUser() {
        super();
    }
    public A02CNUser(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new A02CNUser(1);
    }

}
