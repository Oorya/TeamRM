package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class B01User extends TicketStateUser implements TicketStatable {
    public B01User(TicketStatable ticketStat) {
        super(ticketStat);
    }
}
