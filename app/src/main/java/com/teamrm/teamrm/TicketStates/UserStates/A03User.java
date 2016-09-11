package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class A03User extends TicketStateUser implements TicketStateAble {
    public A03User(TicketStateAble ticketStat) {
        super(ticketStat);
    }
    private void sendNotify()
    {
        //send  notification Confirmation date 
    }
}
