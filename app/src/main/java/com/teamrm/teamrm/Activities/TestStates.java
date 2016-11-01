package com.teamrm.teamrm.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.AdminStates.A00Admin;
import com.teamrm.teamrm.TicketStates.AdminStates.A01Admin;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Utility.UtlNotification;

public class TestStates extends AppCompatActivity {

    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_states);
        context=this;
    }

    public void btnStateA01(View view) {
        A01Admin a01=new A01Admin(1);
        a01.getNewState(ProductID.TICKET_ID_A00A);
    }

    public void btnStateA02(View view) {
        //TicketFactory.registerProduct("hjhj",new A02CNAdmin(2));
        TicketFactory ticketFactory=new TicketFactory();
        ticketFactory.getNewState("gfgj");
    }
    public void btnStateA03(View view) {
        //TicketFactory.registerProduct("hjhj",new A03User(2));

        Intent homeScreen = new Intent(this,HomeScreen.class);
        UtlNotification utlNotification = new UtlNotification(R.drawable.new_msg_icon,"נפתחה קריאה חדשה","יום נפלא",homeScreen);
        utlNotification.sendNotification();
    }

    public void btnStateB01(View view) {
        TicketFactory.registerProduct("fdfd",new A00Admin(5));
    }

    public void btnStateB02(View view) {
        A00Admin ticketFactory=new A00Admin(5);
        ticketFactory.getNewState("gfgj");
    }

    public void btnStateB03(View view) {

    }
}
