package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Interfaces.ProductID;
/**
 * Created by root on 01/09/2016.
 */
public class A03Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_TECH_A03,new A03Tech());
    }
    public A03Tech() {
        super();
    }
    public A03Tech(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new A03Tech(1);
    }
}
