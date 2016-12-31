package com.teamrm.teamrm.TicketStates.AdminStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E03Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_E03,new E03Admin());
    }
    public E03Admin() {
        super();
    }
    public E03Admin(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification("זמן הטיפול הגיע, הטכנאי לא התחיל טיפול","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState(Ticket ticket) {
        return new E03Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
