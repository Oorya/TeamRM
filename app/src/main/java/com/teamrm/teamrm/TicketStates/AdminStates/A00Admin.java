package com.teamrm.teamrm.TicketStates.AdminStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class A00Admin extends TicketStateAdmin implements TicketStateAble {
    
    public A00Admin(String userType) {
        super(userType);
    }
}
