package com.teamrm.teamrm.Interfaces;

/**
 * Created by root on 01/09/2016.
 */
public interface TicketStateAble {
    
    String FOREWORD_STAT="A0U1";
    String AFTER_USER_OPEN="A0U1";
    
    TicketStateAble getNewState(String nextStat, TicketStateAble ticketStat);
         
}
