package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;

/**
 * Created by root on 01/09/2016.
 */
public class B03Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new B03Tech());
    }
    public B03Tech() {
        super();
    }
    public B03Tech(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new B03Tech();
    }
}
