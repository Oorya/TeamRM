package com.teamrm.teamrm.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.UUID;

public class TestStates extends AppCompatActivity {

    TicketFactory ticketFactory;
    EditText txt,txt1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_states);
        ticketFactory=new TicketFactory();
        txt=(EditText)findViewById(R.id.txt);
        txt1=(EditText)findViewById(R.id.txt1);
    }
    private String getUUID()
    {
        //create a unique UUID
        UUID idOne = UUID.randomUUID();
        //returning the UUID
        return idOne.toString();
    }
    public void btnStateA01(View view)
    {
        ticketFactory.getNewState("AdminStates.", ProductID.STATE_ADMIN_A01);
    }

    public void btnStateA02(View view)
    {

    }

    public void btnStateA03(View view)
    {
        UtlFirebase.changeState("55555",txt.getText().toString());
    }

    public void btnStateB01(View view)
    {
        UtlFirebase.changeState("11111",txt.getText().toString());
    }

    public void btnStateB02(View view)
    {
        Ticket ticket = new Ticket(txt1.getText().toString(),"oorya", "A01","Hot");
        ticket.saveTest();
    }

    public void btnStateB03(View view)
    {
        Ticket ticket = new Ticket(txt1.getText().toString(),"yosi", "A03","Hot");
        ticket.saveTest();
    }
}
