package com.teamrm.teamrm.Interfaces;


import com.teamrm.teamrm.Type.Ticket;

import java.util.List;

/**
 * Created by אוריה on 08/08/2016.
 */
public interface FireBaseAble
{
    public void result(Ticket ticket);
    public void result(List<Ticket> ticket);
}
