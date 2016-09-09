package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateTech;

/**
 * Created by root on 01/09/2016.
 */
public class A00Tech extends TicketStateTech implements TicketStatable {
    public A00Tech(TicketStatable ticketStat) {
        super(ticketStat);
    }
}
