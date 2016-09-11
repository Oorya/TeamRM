package com.teamrm.teamrm.Interfaces;

import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.TicketStates.TicketStateUser;

/**
 * Created by root on 01/09/2016.
 */
public interface TicketStatable {
    
    String FOREWORD_STAT="A0U1";
    String AFTER_USER_OPEN="A0U1";
    
    TicketStatable getNewState(String nextStat, TicketStatable ticketStat);
         
}
