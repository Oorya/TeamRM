package com.teamrm.teamrm.Interfaces;

import android.view.View;

/**
 * Created by root on 01/09/2016.
 */
public interface TicketStateAble {
    int TICKET_LIST_STATUS_URGENT = 1;
    int TICKET_LIST_STATUS_PENDING = 2;
    int TICKET_LIST_STATUS_OK = 3;
    int TICKET_LIST_STATUS_ERROR = 4;
    int TTL_SEND_DATE = 5;
    int WAITING_FOR_USER_APPROVAL = 6;
    int WAITING_FOR_TECH_APPROVAL = 7;
    int TTL_END_TICKET_DATE = 8;
    int TTL_END_TIKCET_TIME_EXTENSION = 9;
    TicketStateAble getNewState();
    View setView(View view);

         
}
