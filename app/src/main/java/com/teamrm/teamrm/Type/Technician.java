package com.teamrm.teamrm.Type;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by אוריה on 10/08/2016.
 */
public class Technician extends Users {

    public String calenderID;
    public String shifts;

    public Technician(){}

    public Technician(String userName, String email, String address, String phone, String userID,
                      String calenderID, String shifts) {
        super(userName, email, address, phone,userID);
        this.calenderID=calenderID;
        this.shifts=shifts;
    }

    public void saveTech()
    {
        //create an instance of User class
        Users technician=new Technician(userName,"email","Address","phone",userId,"calederID","Shifts");

        //creating a connection to fire base
        FirebaseDatabase database= FirebaseDatabase.getInstance();

        //creating a reference to Users object
        DatabaseReference myRef=database.getReference("Technician");

        //saving the user under the UUID
        myRef.child(userName).setValue(technician);
    }
}
