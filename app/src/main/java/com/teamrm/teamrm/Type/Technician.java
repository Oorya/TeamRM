package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Technician extends Users {

    // inherited from Users -> userID, userName, email, phone, address, company
    private String
            techCalendarID,
            techCalendarName,
            techColor,
            techShifts;

    public Technician(){}

    public Technician(String userID, String userNameString, String userEmail, String userPhone, String techCalendarID, String techCalendarName, String techColor, String techShifts) {
        super(userID, userNameString, userEmail, userPhone);
        this.techCalendarID = techCalendarID;
        this.techCalendarName = techCalendarName; //TODO: get calendar name by ID? *MARK
        this.techColor = techColor;
        this.techShifts = techShifts;
    }

    public String getTechCalendarID() {
        return techCalendarID;
    }

    public void setTechCalendarID(String techCalendarID) {
        this.techCalendarID = techCalendarID;
        //TODO: add routine for binding a calendar and setting a name *MARK
    }

    public String getTechCalendarName() {
        return techCalendarName;
    }

    public void setTechCalendarName(String techCalendarName) {
        this.techCalendarName = techCalendarName;
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
}
