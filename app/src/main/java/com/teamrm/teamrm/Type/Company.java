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

public class Company implements GenericKeyValueTypeable {
    private String companyName;
    private String companyID;
    private String companyAdminID;
    private String companyPhone;
    private String companyAddress;
    private String companyMail;
    private String companyCreationTime;
    private static List<Company> companyList = new ArrayList<>();

    public Company() {
    }

    public Company(String companyName, String companyAddress, String companyPhone, String companyMail) {
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPhone = companyPhone;
        this.companyMail = companyMail;
        this.companyCreationTime = getCurrentTime();
    }

    public static List<Company> getCompanyList() {
        return companyList;
    }

    public static void setCompanyList(List<Company> companyList) {
        Company.companyList = companyList;
    }

    public static void clearCompanyList(){
        if (null != companyList){
            companyList.clear();
        }
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getCompanyAdminID() {
        return companyAdminID;
    }

    public void setCompanyAdminID(String companyAdminID) {
        this.companyAdminID = companyAdminID;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public String toString() {
        return companyName;
    }


    @Override
    @Exclude
    public String getItemKey() {
        return this.companyID;
    }

    @Override
    @Exclude
    public String getItemValue() {
        return this.companyName;
    }
}
