package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateTech;

/**
 * Created by root on 01/09/2016.
 */
public class B01Tech extends TicketStateTech implements TicketStateAble {
    public B01Tech(TicketStateAble ticketStat) {
        super(ticketStat);
        sendNotify();
    }
    private void sendNotify()
    {
        //send  notification approve  a new ticket
       

    }
}
