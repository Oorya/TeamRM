package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class A03Admin extends TicketStateAdmin implements TicketStateAble {
    
    
    public A03Admin(String userType) {
        super(userType);
        ttl();
    }
    private void ttl()
    {
        //ttl  after 8 Hours 
    }
}
