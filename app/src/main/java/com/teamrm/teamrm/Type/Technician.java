package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Technician extends Users {

    public String calenderID;
    public String shifts;

    public Technician(){}

    public Technician(String userName, String email, String address, String phone, String userID,
                      String calenderID, String shifts) {
        super(userName, email,userID);
        this.calenderID=calenderID;
        this.shifts=shifts;
    }
}
