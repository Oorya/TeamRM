package com.teamrm.teamrm.TicketStates.AdminStates;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class B01Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_B01,new B01Admin());
    }
    public B01Admin() {
        super();
    }
    public B01Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("לקוח אישר מועד","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new B01Admin(1);
    }
}
