package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;

/**
 * Created by root on 01/09/2016.
 */
public abstract class TicketStateTech extends TicketFactory {

   
    private String userType;
    
    public TicketStateTech(String userType) {

        this.userType = userType;
        
    }
    public String getUserType() {
        return userType;
    }

}
