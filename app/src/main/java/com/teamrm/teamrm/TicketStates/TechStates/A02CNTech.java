package com.teamrm.teamrm.TicketStates.TechStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Type.Ticket;

/**
 * Created by root on 01/09/2016.
 */
public class A02CNTech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_TECH_A02CN,new A02CNTech());
    }
    public A02CNTech() {
        super();
    }
    public A02CNTech(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new A02CNTech(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
