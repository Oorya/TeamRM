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
public class B03Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_TECH_B03,new B03Tech());
    }
    public B03Tech() {
        super();
    }
    public B03Tech(int ttl)
    {
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new B03Tech(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
