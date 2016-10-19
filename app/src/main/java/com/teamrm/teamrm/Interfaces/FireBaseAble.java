package com.teamrm.teamrm.Interfaces;


import com.teamrm.teamrm.Type.Ticket;

import java.util.List;

/**
 * Created by Oorya on 08/08/2016.
 */
public interface FireBaseAble
{
    void resultTicket(Ticket ticket);
    void resultList(List<Ticket> ticket);
    void resultBoolean(boolean bool);
}
