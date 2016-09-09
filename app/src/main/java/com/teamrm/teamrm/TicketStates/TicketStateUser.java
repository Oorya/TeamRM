package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;

/**
 * Created by root on 01/09/2016.
 */
public abstract  class TicketStateUser extends TicketFactory {

    private TicketStatable ticketStat;
    
    public TicketStateUser(TicketStatable ticketStat) {
        
        this.ticketStat = ticketStat;
    }
}
