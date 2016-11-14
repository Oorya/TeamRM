package com.teamrm.teamrm.TicketStates;

import com.teamrm.teamrm.Interfaces.TicketStateAble;

import java.util.HashMap;

/**
 * Created by root on 01/09/2016.
 */
public class TicketFactory
{

    private static HashMap m_RegisteredProducts = new HashMap();
    


    public TicketFactory(){}

    public TicketStateAble getNewState(String stateType, String nextStateName)
    {
        //Checking if class was recorded in HashMap if not force her to sign up 
        if(m_RegisteredProducts.get(nextStateName)==null)
        {
            try {
                Class.forName("com.teamrm.teamrm.TicketStates."+stateType+nextStateName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ((TicketStateAble)m_RegisteredProducts.get(nextStateName)).getNewState();
    }

    public static void registerProduct(String ticketID, TicketStateAble T)
    {
        m_RegisteredProducts.put(ticketID, T);
    }




}
