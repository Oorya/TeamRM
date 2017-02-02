package com.teamrm.teamrm.TicketStates.TechStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E00Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_TECH_E00,new E00Tech());
    }
    public E00Tech() {
        super();
    }
    public E00Tech(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("קריאה בוטלה","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E00Tech(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
