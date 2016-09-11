package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;

/**
 * Created by root on 01/09/2016.
 */
public abstract class TicketStateAdmin extends TicketFactory {
    
    private String userType;
    
    public TicketStateAdmin(String userType) {
        
        this.userType = userType;
    }
    public String getUserType()
    {
        return this.userType;
    }
}
