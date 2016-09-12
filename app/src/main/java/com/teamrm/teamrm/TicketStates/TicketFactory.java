package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.AdminStates.A00Admin;
import com.teamrm.teamrm.TicketStates.TechStates.A00Tech;
import com.teamrm.teamrm.TicketStates.UserStates.A00User;

import java.util.HashMap;

/**
 * Created by root on 01/09/2016.
 */
public class TicketFactory
{

    private static HashMap m_RegisteredProducts = new HashMap();

    public TicketFactory(){}

    public TicketStateAble getNewState(String TICKET_ID)
    {
        return ((TicketStateAble)m_RegisteredProducts.get(TICKET_ID)).getNewState();

    }
    public static void registerProduct(String TICKET_ID, TicketStateAble T)
    {
        m_RegisteredProducts.put(TICKET_ID, T);
    }




}
