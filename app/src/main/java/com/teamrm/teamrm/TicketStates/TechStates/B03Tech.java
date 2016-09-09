package com.teamrm.teamrm.TicketStates.TechStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateTech;

/**
 * Created by root on 01/09/2016.
 */
public class B03Tech extends TicketStateTech implements TicketStatable {
    public B03Tech(TicketStatable ticketStat) {
        super(ticketStat);
    }
}
