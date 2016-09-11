package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.AdminStates.A00Admin;
import com.teamrm.teamrm.TicketStates.TechStates.A00Tech;
import com.teamrm.teamrm.TicketStates.UserStates.A00User;

/**
 * Created by root on 01/09/2016.
 */
public class TicketFactory implements TicketStateAble
{
    public TicketFactory ( ) {}

    @Override
    public TicketStateAble getNewState(String newState, TicketStateAble ticketState)
    {
        
        switch (newState)
        {
            case TicketStateAble.FORWARD_STATE:
                switch(((TicketStateAdmin)ticketState).getUserType())
                    {
                        case "admin": 
                             return new A00Admin("admin");
                        case "user":
                            return new A00User("user");
                        case "tech":
                            return new A00Tech("tech");
                    }
            default:
                return null;
        }
    }

    

}
