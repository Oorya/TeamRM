package com.teamrm.teamrm.Type;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oorya on 15/12/2016.
 */

public class Company
{
    private String companyName;
    private String companyId;
    private String adminId;
    private String phone;
    private String address;
    private String time;
    private Category category;
    private Product product;

    public Company(){}

    public Company(String companyId, String companyName, String adminId, String address, String phone) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.adminId = adminId;
        this.address = address;
        this.phone = phone;
        this.time = getCurrentTime();
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getTime() {
        return time;
    }

    public Category getCategory() {
        return category;
    }

    public Product getProduct()
    {
        return product;
    }
}
