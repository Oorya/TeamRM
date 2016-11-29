package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class B03User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_USER_B03,new B03User());
    }
    public B03User() {
        super();
    }
    public B03User(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("טכנאי התחיל טיפול","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new B03User(1);
    }

}
