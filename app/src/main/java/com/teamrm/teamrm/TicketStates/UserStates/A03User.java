package com.teamrm.teamrm.TicketStates.UserStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class A03User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_USER_A03,new A03User());
    }
    public A03User() {
        super();
    }
    public A03User(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("נא לאשר מועד","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new A03User(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }

}
