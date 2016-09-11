package com.teamrm.teamrm.TicketStates.UserStates;

import com.teamrm.teamrm.Interfaces.TicketStatable;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public class A00User extends TicketStateUser implements TicketStatable {
    
    String userType;


    public A00User(String userType) {
        super(userType);
    }
}
