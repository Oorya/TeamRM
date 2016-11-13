package com.teamrm.teamrm.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Type.Ticket;

public class TestStates extends AppCompatActivity {

    public static Context context;
    TicketFactory ticketFactory;
    EditText txt,txt1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_states);
        ticketFactory=new TicketFactory();
        txt=(EditText)findViewById(R.id.txt);
        txt1=(EditText)findViewById(R.id.txt1);

        context=this;
    }

    public void btnStateA01(View view) {

    }

    public void btnStateA02(View view) {
        ticketFactory.getNewState(ProductID.TICKET_CLASS_NAME_A01A);
    }
    public void btnStateA03(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        myRef.child("55555").child("state").setValue(txt.getText().toString());
    }

    public void btnStateB01(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Ticket");

        //myRef.child("11111").child("state").setValue(ProductID.TICKET_CLASS_NAME_A00A);
        myRef.child("11111").child("state").setValue(txt.getText().toString());
    }

    public void btnStateB02(View view) {
        Ticket ticket = new Ticket(txt1.getText().toString(),"oorya", "A01","Hot");
        ticket.saveTest();
    }

    public void btnStateB03(View view) {
        Ticket ticket = new Ticket(txt1.getText().toString(),"yosi", "A00","Hot");
        ticket.saveTest();
    }
}
