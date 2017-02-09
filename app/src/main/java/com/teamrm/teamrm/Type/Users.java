package com.teamrm.teamrm.Type;

/**
 * Created byאOorya on 07/07/2016.
 */
public abstract class Users
{
    public static final String STATUS_ADMIN = "Admin";
    public static final String STATUS_TECH = "Tech";
    public static final String STATUS_CLIENT = "Client";
    private String userID;
    private String userNameString;
    private String userEmail;
    private boolean isUserAdmin;
    private String userStatus;
    private String userPhone;
    private String userAddress;
    private String assignedCompanyID;


    public Users(){}  //empty constructor, must have

    public Users(String userID, String userNameString, String userEmail) {
        this.userID = userID;
        this.userNameString = userNameString;
        this.userEmail = userEmail;
        this.userStatus = STATUS_CLIENT;
        this.isUserAdmin = false;
    }

    public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isUserAdmin() {
        return isUserAdmin;
    }

    public void setUserAdmin(boolean userAdmin) {
        this.isUserAdmin = userAdmin;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getAssignedCompanyID() {
        return assignedCompanyID;
    }

    public void setUserCompany(String userCompany) {
        this.assignedCompanyID = userCompany;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

   }
