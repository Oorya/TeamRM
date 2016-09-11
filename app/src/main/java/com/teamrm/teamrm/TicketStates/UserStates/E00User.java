package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class E00User extends TicketStateUser implements TicketStateAble {
    public E00User(TicketStateAble ticketStat) {
        super(ticketStat);
    }
    private void ClosesTicket()
    {
        //SEND NOTIFY  TICKET Closes Success
    }

}
