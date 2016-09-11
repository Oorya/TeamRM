package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class C01Admin extends TicketStateAdmin implements TicketStateAble {
    public C01Admin(String userType) {
        super(userType);
    }
}
