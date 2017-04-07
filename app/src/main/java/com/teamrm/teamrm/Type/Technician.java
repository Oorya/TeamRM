package com.teamrm.teamrm.Type;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Technician extends Users implements GenericKeyValueTypeable {

    private static List<Technician> technicianList = new ArrayList<>();

    // inherited from Users -> userID, clientNameString, userEmail, ticketPhone, ticketAddress, companyName
    private String
            techColor,
            techShifts,
            techAssignedRegions;

    public Technician() {
    }

    public Technician(String userID, String userNameString, String userEmail, @Nullable String userPhone, @Nullable String userAddress,
                      String techCalendarID, String techCalendarName, String techColor, String techShifts, String techAssignedRegions) {
        super(userID, userNameString, userEmail);
        this.techColor = techColor;
        this.techShifts = techShifts;
        this.techAssignedRegions = techAssignedRegions;
    }

    public String getTechColor() {
        return techColor;
    }

    public void setTechColor(String techColor) {
        this.techColor = techColor;
    }

    public String getTechShifts() {
        return techShifts;
    }

    public void setTechShifts(String techShifts) {
        this.techShifts = techShifts;
    }

    public String getTechAssignedRegions() {
        return techAssignedRegions;
    }

    public void setTechAssignedRegions(String techAssignedRegions) {
        this.techAssignedRegions = techAssignedRegions;
    }

    public static List<Technician> getTechnicianList() {
        return technicianList;
    }

    public static void setTechnicianList(List<Technician> technicianList) {
        Technician.technicianList = technicianList;
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
