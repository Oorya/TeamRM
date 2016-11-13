package com.teamrm.teamrm.TicketStates.UserStates;

import android.content.Intent;

import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Interfaces.TicketStateAble;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.TicketStates.TicketStateUser;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Utility.UtlNotification;

/**
 * Created by root on 01/09/2016.
 */
public class C01User extends TicketStateUser implements TicketStateAble {
    static {
        TicketFactory.registerProduct(ProductID.TICKET_CLASS_NAME_A00A,new C01User());
    }
    public C01User() {
        super();
    }
    public C01User(int ttl)
    {
        //initials ttl example
        Intent homeScreen = new Intent(HomeScreen.context,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(1,"קריאתך נסגרה בהצלחה, נא לאשר סגירה","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    @Override
    public TicketStateAble getNewState() {
        return new C01User(1);
    }

}
