package com.teamrm.teamrm.TicketStates.AdminStates;

import android.util.Log;
import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Type.Ticket;

/**
 * Created by root on 01/09/2016.
 */
public class A00Admin extends TicketStateAdmin implements TicketStateAble {


    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_A00,new A00Admin());
        Log.d("registerProduct = ",ProductID.STATE_ADMIN_A00);

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
    public TicketStateAble getNewState(Ticket ticket) {
        return new A00Admin(1);
    }

    @Override
    public View setView(View view) {
        return null;
    }


}
