package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class E01Admin extends TicketStateAdmin implements TicketStateAble {
    public E01Admin(TicketStateAble ticketStat) {
        super(ticketStat);
        
    }
    private void sendNotify()
    {
        //send  notification ticket clo's by user 
    }
    private void saveToData()
    {
        //SAVE TICKET IN SQL AND DELETE TICKET
    }
}
