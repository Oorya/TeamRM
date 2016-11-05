package com.teamrm.teamrm.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.TicketFactory;

public class TestStates extends AppCompatActivity {

    public static Context context;
    TicketFactory ticketFactory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_states);
         ticketFactory=new TicketFactory();
        context=this;
    }

    public void btnStateA01(View view) {
        ticketFactory.getNewState(ProductID.TICKET_CLASS_NAME_A00A);
    }

    public void btnStateA02(View view) {
        ticketFactory.getNewState(ProductID.TICKET_CLASS_NAME_A01A);

    }
    public void btnStateA03(View view) {
        
    }

    public void btnStateB01(View view) {
        
    }

    public void btnStateB02(View view) {
       
    }

    public void btnStateB03(View view) {

    }
}
