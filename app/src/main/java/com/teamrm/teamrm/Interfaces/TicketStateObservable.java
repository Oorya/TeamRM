package com.teamrm.teamrm.Interfaces;

import com.teamrm.teamrm.Type.TicketState;

/**
 * Created by root on 19/02/2017.
 */

public interface TicketStateObservable {
    void onTicketAdded(TicketState ticketState);
    void onTicketStateChanged(TicketState ticketState);
    void onTicketRemoved(TicketState ticketState);
}
