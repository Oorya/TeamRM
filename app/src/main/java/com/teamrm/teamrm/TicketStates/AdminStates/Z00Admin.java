package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class Z00Admin extends TicketStateAdmin implements TicketStatable {
    public Z00Admin(TicketStatable ticketStat) {
        super(ticketStat);
    }
}
