package com.teamrm.teamrm.TicketStates.UserStates;

import android.content.Intent;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Activities.TestStates;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class A01User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.STATE_USER_A01,new A01User());
    }
    public A01User() {
        super();
    }
    public A01User(int ttl)
    {
        //initials ttl example
        Intent homeScreen = new Intent(TestStates.context,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(R.drawable.new_msg_icon,"קריאתך נפתחה בהצלחה","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new A01User(1);
    }
}
   