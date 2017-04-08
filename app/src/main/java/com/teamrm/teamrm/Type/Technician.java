package com.teamrm.teamrm.Type;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Utility.UserSingleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Technician extends Users implements GenericKeyValueTypeable {

    private static List<Technician> technicianList = Collections.EMPTY_LIST;

    // inherited from Users -> userID, clientNameString, userEmail, ticketPhone, ticketAddress, companyName
    private String techColor;
    private String techAssignedShifts;
    private String techAssignedRegions;
    private String techEnrollmentCode;

    private boolean isEdited;

    public Technician() {
    }

    public Technician(String userID, String userNameString, String userEmail, @Nullable String userPhone, @Nullable String userAddress,
                      String techColor, String techAssignedShifts, String techAssignedRegions) {
        super(userID, userNameString, userEmail);
        this.techColor = techColor;
        this.techAssignedShifts = techAssignedShifts;
        this.techAssignedRegions = techAssignedRegions;
    }

    public Technician(String enrollmentCode, String companyID, Users user) {
        super(user);
        this.setAssignedCompanyID(companyID);
        this.techEnrollmentCode = enrollmentCode;
        this.techColor = "";
        this.techAssignedRegions = "";
        this.techAssignedShifts = "";
        this.isEdited = false;
    }

    public String getTechColor() {
        return techColor;
    }

    public void setTechColor(String techColor) {
        this.techColor = techColor;
    }

    public String getTechAssignedShifts() {
        return techAssignedShifts;
    }

    public void setTechAssignedShifts(String techAssignedShifts) {
        this.techAssignedShifts = techAssignedShifts;
    }

    public String getTechAssignedRegions() {
        return techAssignedRegions;
    }

    public void setTechAssignedRegions(String techAssignedRegions) {
        this.techAssignedRegions = techAssignedRegions;
    }

    public String getTechEnrollmentCode() {
        return techEnrollmentCode;
    }

    public void setTechEnrollmentCode(String techEnrollmentCode) {
        this.techEnrollmentCode = techEnrollmentCode;
    }


    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public static List<Technician> getTechnicianList() {
        return technicianList;
    }

    public static void setTechnicianList(List<Technician> technicianList) {
        Technician.technicianList = technicianList;
    }

    public static void addTechnicianToList(Technician technician) {
        technicianList.add(technician);
    }

    public static void removeTechnicianFromList(Technician technician) {
        //TODO:add method

    }

    public static void changeTechnician(Technician technician) {
        //TODO:Add method
    }


    @Override
    @Exclude
    public String getItemKey() {
        return super.getUserID();
    }

    @Override
    @Exclude
    public String getItemValue() {
        return super.getUserNameString();
    }
}
