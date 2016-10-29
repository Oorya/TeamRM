package com.teamrm.teamrm.Interfaces;

/**
 * Created by root on 01/09/2016.
 */
public interface TicketStateAble {
    int TICKET_LIST_PENDIN_APPROVAL = 1;
    int  TICKET_LIST_PENDIN_TECH_ITEM = 2;
    int  TICKET_LIST_PENDIN_TREATMENT =3;
    
    TicketStateAble getNewState();
         
}
