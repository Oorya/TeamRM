package com.teamrm.teamrm.TicketStates.UserStates;

import android.content.Intent;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlFirebase;
import com.teamrm.teamrm.Utility.UtlNotification;

import java.util.List;

/**
 * Created by root on 01/09/2016.
 */
public class A01User extends TicketStateUser implements TicketStateAble, FireBaseAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_ID_A00T,new A01User());
    }
    public A01User() {
        super();
    }
    public A01User(int ttl)
    {
        //send ticket id, check if ticket exist
        UtlFirebase.ticketSaved("SEND TICKET ID",this);
        //initials ttl example
    }

    @Override
    public TicketStateAble getNewState() {
        return new A01User();
    }


    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultList(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean ticketExist) {
        Intent homeScreen = new Intent(HomeScreen.context,HomeScreen.class);
        if(ticketExist)
        {
            UtlNotification utlNotification = new UtlNotification(1,"קריאתך נפתחה בהצלחה","יום נפלא",homeScreen);
            utlNotification.sendNotification();
        }
        else
        {
            UtlNotification utlNotification = new UtlNotification(1,"פתיחת קריאה נכשלה","יום נפלא",homeScreen);
            utlNotification.sendNotification();
        }
    }
}
   