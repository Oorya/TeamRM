package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E06Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_E06,new E06Admin());
    }
    public E06Admin() {
        super();
    }
    public E06Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("התראה: הקריאה לא טופלה בהצלחה","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new E06Admin(1);
    }
}
