package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;

/**
 * Created by root on 01/09/2016.
 */
public class TicketFactory implements TicketStatable
{
    public TicketFactory ( ) {}

    @Override
    public TicketStatable nextStat(String nextStat,TicketStatable ticketStat)
    {
        
        switch (nextStat)
        {
            case TicketStatable.TICKET_STAT:
                return nextTicketStata();
            /*
             .
             .
             .
             .
             .
             */
            default:
                return null;
        }
    }

    private TicketStatable nextTicketStata() //All status and his on method
    {
        return null;
    }
}
