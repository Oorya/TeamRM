package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class B01Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_TECH_B01,new B01Tech());
    }
    public B01Tech() {
        super();
    }
    public B01Tech(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("מועד טיפול חדש, נא לאשר","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new B01Tech(1);
    }
}
