package com.teamrm.teamrm.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Oorya on 15/12/2016.
 */

public class Company
{
    public String name;
    public String companyId;
    public String adminId;
    public String phone;
    public String address;
    public String time;

    public Company(){}

    public Company(String name, String adminId, String address, String phone) {
        this.name = name;
        this.companyId = getUUID();
        this.adminId = adminId;
        this.address = address;
        this.phone = phone;
        this.time = getCurrentTime();
    }


    private String getUUID()
    {
        //create a unique UUID
        UUID idOne = UUID.randomUUID();
        //returning the UUID
        return idOne.toString();
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
}
