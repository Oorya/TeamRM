package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class A01Admin extends TicketStateAdmin implements TicketStatable {
    public A01Admin(TicketStatable ticketStat) {
        super(ticketStat);
    }
    private void alertNewTicket()
    {
       //SEND NOTIFY NEW TICKET 
    }
}
