package com.teamrm.teamrm.Type;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamrm.teamrm.Activities.MainActivity;
import com.teamrm.teamrm.Interfaces.ProductID;
import com.teamrm.teamrm.Interfaces.TicketStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by אוריה on 18/07/2016.
 */
public class Ticket {
    public String customerName;
    public String email;
    public String tech;
    public String product;
    public String classification;
    public String desShort;
    public String desLong; //Description
    public String area;
    public String address;
    public String ticketImage1;
    public String ticketImage2;
    public String phone;
    public String ticketId;
    public String state;
    public String startTime;
    public Date endTime;
    public Date ttl;
    public int alarmID;
    public int repeatSendCounter;
    public String ticketNumber;
    public String calenderID;
    public String eventID;
    public String company;
    public String statusA;
    public int status;

    public Ticket(){}  //empty constructor, must have

    public Ticket(String company, String product, String classification, String area, String address, String phone, String desShort, String desLong
                 ,String ticketImage1, String ticketImage2,String ticketId)
    {
        this.customerName = MainActivity.userName;   //Taking from login
        this.email=MainActivity.email; //Taking from login
        this.company=company;
        this.product=product;
        this.classification=classification;
        this.area=area;
        this.address=address;
        this.phone=phone;
        this.desShort=desShort;
        this.desLong=desLong;
        this.ticketImage1=ticketImage1;
        this.ticketImage2=ticketImage2;
        this.ticketId=ticketId;
        this.startTime =getCurrentTime();
        this.state= ProductID.STATE_A00;
        this.statusA= TicketStatus.waitForApproval;
        this.tech="אין טכנאי מצוות";
        this.ticketNumber=ticketId.substring(0,8);
        this.status=1;
    }

    public void saveTicket(Ticket ticket)
    {
        //create an instance of User class
       // Ticket ticket=new Ticket(company,product,classification,area,address,phone,desShort,desLong,ticketImage1,ticketImage2,ticketId);

        //creating a connection to fire base
        FirebaseDatabase database= FirebaseDatabase.getInstance();

        //creating a reference to Users object
        DatabaseReference myRef=database.getReference("Ticket");

        //saving the user under the UUID
        myRef.child(ticketId).setValue(ticket);
    }

    private String getCurrentTime()
    {
        //Calendar calendar=Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        //get current date startTime with Date()
        Date date = new Date();
        //return dateFormat.format(cal.getTime()));
        return dateFormat.format(date);
    }


    public String toString()
    {
        String str="";
        str+="Ticket name: "+this.product+"\n";
        str+="Ticket description: "+this.desShort+"\n";
        str+="Phone: "+this.phone+"\n";
        str+="User name: "+this.customerName+"\n";
        str+="Status: "+this.status+"\n";
        return str;
    }
    public int getRepeatSendCounter() {
        return repeatSendCounter;
    }

    public void incCounter() {

        //מתודה שמעדקנת את השדה repeatSendCounter בפיירבייס
        this.repeatSendCounter += repeatSendCounter;
    }
    public void incInitialization() {

        //מתודה שמעדקנת את השדה repeatSendCounter בפיירבייס
        this.repeatSendCounter = 0;
    }
}
