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
public class E04Tech extends TicketStateTech implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00A,new E04Tech());
    }
    public E04Tech() {
        super();
    }
    public E04Tech(int ttl)
    {
        //initials ttl example
        Intent homeScreen = new Intent(HomeScreen.context,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(1,"טיפול נדחה ברבע שעה","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new E04Tech(1);
    }
}
