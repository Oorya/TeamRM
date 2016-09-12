package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.ProductID1111;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;

/**
 * Created by root on 01/09/2016.
 */
public class E01Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID1111.TICKET_ID_A00A,new E01Tech());
    }
    public E01Tech() {
        super();
    }
    public E01Tech(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new E01Tech();
    }
}
