package com.teamrm.teamrm.TicketStates;

import android.util.Log;

import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.Type.Ticket;

import java.util.HashMap;

/**
 * Created by root on 01/09/2016.
 */
public class TicketFactory
{
    private static HashMap m_RegisteredProducts = new HashMap();

    public TicketFactory(){}

    /*public TicketStateAble getNewState(String stateType, String nextStateName, Ticket ticket)
    {
        Log.d("FactorystateType = ", nextStateName+stateType);
        Log.d("FactorFULtYPE","com.teamrm.teamrm.TicketStates."+stateType+"States."+nextStateName+stateType);
        //Checking if class was recorded in HashMap if not force her to sign up 
        if(m_RegisteredProducts.get(nextStateName)==null)
        {
            try {

                Class.forName("com.teamrm.teamrm.TicketStates."+stateType+"States."+nextStateName+stateType);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ((TicketStateAble)m_RegisteredProducts.get(nextStateName+stateType)).getNewState(ticket);
    }*/

    public TicketStateAble getNewState(String stateType, String nextStateName, Ticket ticket) {
        //Checking if class was recorded in HashMap if not force her to sign up
        if (m_RegisteredProducts.get(nextStateName) == null) {
            try {
                Class.forName("com.teamrm.teamrm.TicketStates." + stateType + nextStateName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ((TicketStateAble) m_RegisteredProducts.get(nextStateName)).getNewState(ticket);

    }

    public static void registerProduct(String ticketID, TicketStateAble T)
    {
        Log.d("FactorregisterProduct= ", ticketID);

        m_RegisteredProducts.put(ticketID, T);
    }
}
