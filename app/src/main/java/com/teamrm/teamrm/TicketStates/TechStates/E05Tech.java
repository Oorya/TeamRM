package com.teamrm.teamrm.TicketStates.TechStates;

import android.content.Intent;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateTech;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class E05Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new E05Tech());
    }
    public E05Tech() {
        super();
    }
    public E05Tech(int ttl)
    {
        //initials ttl example
        Intent homeScreen = new Intent(HomeScreen.context,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(1,"התראה: זמן הטיפול תם","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new E05Tech(1);
    }
}
