package com.teamrm.teamrm.Type;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by אוריה on 18/07/2016.
 */
public class Ticket {
    public String customerName;
    public String email;
    public String tech;
    public String product;
    public String classification;
    public String subClassification;
    public String ticketDes; //Description
    public String area;
    public String address;
    public String ticketImage1;
    public String ticketImage2;
    public String phone;
    public String ticketId;
    public String state;
    public String time;
    public Date endTime;
    public String ticketNumber;
    public String calenderID;
    public String eventID;
    public String company;
    public int status;

    public Ticket(){}  //empty constructor, must have

    //FROM TESTING ONLY!!!
    public Ticket(String name,int status)
    {
        this.status = status;
        this.customerName = name;
    }

    //FROM TESTING ONLY!!!
    public Ticket(String ticketId, String userName, String state, String company)
    {
        this.ticketId=ticketId;
        this.customerName=userName;
        this.state=state;
        this.company=company;
    }
    
    public Ticket(String email, String product, String classification, String subClassification, String ticketDes,
                  String phone, String area, String address, String ticketImage1, String ticketImage2)
    {
        this.customerName = "TEST";//Taking from login
        this.email=email;
        this.product=product;
        this.classification=classification;
        this.subClassification=subClassification;
        this.ticketDes=ticketDes;
        this.area=area;
        this.address=address;
        this.ticketImage1=ticketImage1;
        this.ticketImage2=ticketImage2;
        this.phone=phone;
        this.ticketId=getUUID();
        this.time=getCurrentTime();
        this.ticketNumber=ticketId.substring(0,8);
    }

    public void saveTicket()
    {
        //create an instance of User class
        Ticket ticket=new Ticket(email,product,classification,subClassification,ticketDes, phone
               ,area, address, ticketImage1,ticketImage2);

        //creating a connection to fire base
        FirebaseDatabase database= FirebaseDatabase.getInstance();

        //creating a reference to Users object
        DatabaseReference myRef=database.getReference("Ticket");

        //saving the user under the UUID
        myRef.child(ticketId).setValue(ticket);
    }

    //FROM TESTING ONLY!!!
    public void saveTest()
    {
        Ticket ticket=new Ticket(ticketId,customerName,state,company);

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
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd:MM:yyyy");
        //get current date time with Date()
        Date date = new Date();
        //return dateFormat.format(cal.getTime()));
        return dateFormat.format(date);
    }

    private String getUUID()
    {
        //create a unique UUID
        UUID idOne = UUID.randomUUID();
        //returning the UUID
        return idOne.toString();
    }

    public String toString()
    {
        String str="";
        str+="Ticket name: "+this.product+"\n";
        str+="Ticket description: "+this.ticketDes+"\n";
        str+="Phone: "+this.phone+"\n";
        str+="User name: "+this.customerName+"\n";
        str+="Status: "+this.status+"\n";
        return str;
    }
}
