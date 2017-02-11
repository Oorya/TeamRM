package com.teamrm.teamrm.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.TicketStates.TicketFactory;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private String getCurrentTime()
    {
        //Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        //get current date ticketOpenDateTime with Date()
        Date date = new Date();
        //return dateFormat.format(cal.getCompanyCreationTime()));
        return dateFormat.format(date);
    }

    public void btnStateA01(View view)
    {
       // ticketFactory.getNewState("AdminStates.", TicketStateStringable.STATE_ADMIN_A01);
    }

    public void btnStateA02(View view)
    {
        txt.setText(getCurrentTime());
    }

    public void btnStateA03(View view)
    {
        Toast.makeText(this, txt.getText().toString(), Toast.LENGTH_SHORT).show();

        UtlFirebase.updateTicketStateString("a58f3feb-c0d8-48cd-b45b-feb936ccc022", txt.getText().toString());
    }

    public void btnStateB01(View view)
    {
        Toast.makeText(this, MainActivity.userEmail, Toast.LENGTH_SHORT).show();
    }

    public void btnStateB02(View view)
    {

    }

    public void btnStateB03(View view)
    {

    }
}
