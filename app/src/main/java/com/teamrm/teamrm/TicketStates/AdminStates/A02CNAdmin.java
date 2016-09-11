package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class A02CNAdmin extends TicketStateAdmin implements TicketStateAble {
    public A02CNAdmin(String userType) {
        super(userType);
        remainder();
        
    }
    private void alertConfirmedTicket()
    {
        //SEND NOTIFY  TICKET Confirmed  Success
    }
    private void remainder()
    {
        //remainder after 8 Hours
    }
    
    
    
}
