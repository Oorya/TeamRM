package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Interfaces.ProductID;
/**
 * Created by root on 01/09/2016.
 */
public class A02CNTech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_CLASS_NAME_A00A,new A02CNTech());
    }
    public A02CNTech() {
        super();
    }
    public A02CNTech(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new A02CNTech(1);
    }
}
