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
class Z00Tech extends TicketStateTech implements TicketStateAble {
     static {
      TicketFactory.registerProduct(TicketStateStringable.STATE_TECH_Z00,new Z00Tech());
     }

     public Z00Tech() {
      super();
     }
     public Z00Tech(int ttl)
     {
      //initials ttl example
      UtlNotification utlNotification = new UtlNotification("מנהל סגר את הקריאה","יום נפלא");
      utlNotification.sendNotification();
     }

     @Override
     public TicketStateAble getNewState(Ticket ticket) {
      return new Z00Tech(1);
     }

    @Override
    public View setView(View view) {
        return null;
    }
}
