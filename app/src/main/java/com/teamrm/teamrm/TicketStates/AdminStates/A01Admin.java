package com.teamrm.teamrm.TicketStates.AdminStates;

import android.content.Intent;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.TestStates;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class A01Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_ADMIN_A01,new A01Admin());
    }
    public A01Admin() {
        super();
    }

    public A01Admin(int ttl)
    {
        //initials ttl example
        Intent homeScreen = new Intent(TestStates.context,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(R.drawable.new_msg_icon,"נפתחה קריאה חדשה","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new A01Admin(1);
    }
}
