package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class B02Admin extends TicketStateAdmin implements TicketStatable {
    public B02Admin(String userType) {
        super(userType);
    }
    private void sendNotify()
    {
        //send  notification date not  Confirm  by user
        //setup ttl

    }
}
