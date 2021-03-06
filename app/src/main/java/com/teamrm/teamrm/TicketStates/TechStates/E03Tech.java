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
public class E03Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(TicketStateStringable.STATE_TECH_E03,new E03Tech());
    }
    public E03Tech() {
        super();
    }
    public E03Tech(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("זמן הטיפול הגיע, הטכנאי לא התחיל טיפול","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E03Tech(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
