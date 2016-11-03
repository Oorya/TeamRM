package com.teamrm.teamrm.TicketStates.AdminStates;

import android.content.Intent;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateAdmin;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E05Admin extends TicketStateAdmin implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new E05Admin());
    }
    public E05Admin() {
        super();
    }
    public E05Admin(int ttl)
    {
        //initials ttl example
        Intent homeScreen = new Intent(HomeScreen.context,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(1,"התראה: זמן הטיפול תם","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new E05Admin(1);
    }
}
