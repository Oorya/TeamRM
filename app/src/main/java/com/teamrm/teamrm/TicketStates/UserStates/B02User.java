package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class B02User extends TicketStateUser implements TicketStateAble {
    public B02User(TicketStateAble ticketStat) {
        super(ticketStat);
    }
    private void sendNotify()
    {
        //send  notification date  Confirm  Success
        //setup ttl

    }
}
