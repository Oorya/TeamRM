package com.teamrm.teamrm.Interfaces;

import android.view.View;

import com.teamrm.teamrm.Type.Ticket;

/**
 * Created by root on 01/09/2016.
 */
public interface TicketStateAble {
    int TICKET_LIST_PRESENTATION_URGENT = 1;
    int TICKET_LIST_PRESENTATION_PENDING = 2;
    int TICKET_LIST_PRESENTATION_OK = 3;
    int TICKET_LIST_PRESENTATION_ERROR = 4;
    int TTL_SEND_DATE = 5;
    int WAITING_FOR_USER_APPROVAL = 6;
    int WAITING_FOR_TECH_APPROVAL = 7;
    int TTL_END_TICKET_DATE = 8;
    int TTL_END_TIKCET_TIME_EXTENSION = 9;
    int TECH_START_WORK_ON_TICkET = 10;
    TicketStateAble getNewState(Ticket ticket);
    View setView(View view);

         
}
