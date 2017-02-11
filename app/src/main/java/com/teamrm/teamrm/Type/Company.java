package com.teamrm.teamrm.Type;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Oorya on 15/12/2016.
 */

public class Company implements GenericKeyValueTypeable
{
    private String companyName;
    private String companyId;
    private String adminId;
    private String companyPhone;
    private String companyAddress;
    private String companyCreationTime;
    private static List<Company> companyList = new ArrayList<>();

    public Company(){}

    public Company(String companyId, String companyName, String adminId, String companyAddress, String companyPhone) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.adminId = adminId;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.companyCreationTime = getCurrentTime();
    }

    public static List<Company> getCompanyList() {
        return companyList;
    }

    public static void setCompanyList(List<Company> companyList) {
        Company.companyList = companyList;
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

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public String getCompanyCreationTime() {
        return companyCreationTime;
    }

    public String toString()
    {
        return companyName;
    }


    @Override
    @Exclude
    public String getItemKey() {
        return this.companyId;
    }

    @Override
    @Exclude
    public String getItemValue() {
        return this.companyName;
    }
}
