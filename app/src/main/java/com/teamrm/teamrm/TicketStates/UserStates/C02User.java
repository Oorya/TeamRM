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
public class C02User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_USER_C02,new C02User());
    }
    public C02User() {
        super();
    }
    public C02User(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("שמחנו לתת שירות","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new C02User(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }

}
