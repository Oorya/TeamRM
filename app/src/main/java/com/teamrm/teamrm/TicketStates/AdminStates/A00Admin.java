package com.teamrm.teamrm.TicketStates.AdminStates;

import android.util.Log;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;

/**
 * Created by root on 01/09/2016.
 */
public class A00Admin extends TicketStateAdmin implements TicketStateAble {


    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new A00Admin());
        Log.d("registerProduct = ",ProductID.TICKET_ID_A00A);

    }
    public A00Admin() {
        super();
    }
    public A00Admin(int ttl)
    {
        //initials ttl example
        Log.e("TAG", "A001");
    }

    @Override
    public TicketStateAble getNewState() {
        return new A00Admin(1);
    }
}
