package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class E00User extends TicketStateUser implements TicketStatable {
    public E00User(TicketStatable ticketStat) {
        super(ticketStat);
    }
    private void ClosesTicket()
    {
        //SEND NOTIFY  TICKET Closes Success
    }

}
