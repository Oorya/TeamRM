package com.teamrm.teamrm.Interfaces;

/**
 * Created by root on 01/09/2016.
 */
public interface TicketStateAble {
    int TICKET_LIST_STATUS_URGENT = 1;
    int TICKET_LIST_STATUS_PENDING = 2;
    int TICKET_LIST_STATUS_OK = 3;
    int TICKET_LIST_STATUS_ERROR = 4;
    int TTL_SEND_DATE = 1;
    int WAITING_FOR_USER_APPROVAL = 2;
    int WAITING_FOR_TECH_APPROVAL = 3;

    TicketStateAble getNewState();
         
}
