package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class A03Admin extends TicketStateAdmin implements TicketStatable {
    
    
    public A03Admin(String userTaype) {
        super(userTaype);
        ttl();
    }
    private void ttl()
    {
        //ttl  after 8 Hours 
    }
}
