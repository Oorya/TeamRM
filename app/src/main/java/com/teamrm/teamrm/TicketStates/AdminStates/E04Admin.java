package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E04Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_E04,new E04Admin());
    }
    public E04Admin() {
        super();
    }
    public E04Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("טיפול נדחה ברבע שעה","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new E04Admin(1);
    }
}
