package com.teamrm.teamrm.TicketStates.TechStates;

import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E05Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_TECH_E05,new E05Tech());
    }
    public E05Tech() {
        super();
    }
    public E05Tech(int ttl)
    {
        //initials ttl example
        UtlNotification utlNotification = new UtlNotification(1,"התראה: זמן הטיפול תם","יום נפלא");
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new E05Tech(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }
}
