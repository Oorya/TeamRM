package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class A02CNUser extends TicketStateUser implements TicketStateAble {
    public A02CNUser(TicketStateAble ticketStat) {
        super(ticketStat);
    }
    private void alertConfirmedTicket()
    {
        //SEND NOTIFY  TICKET Confirmed  
    }
}
