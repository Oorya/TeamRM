package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class B01Admin extends TicketStateAdmin implements TicketStatable {
    public B01Admin(String userTaype) {
        super(userTaype);
        sendNotify();
    }
    private void sendNotify()
    {
        //send  notification date Confirm  by user
        //setup ttl
    }
}
