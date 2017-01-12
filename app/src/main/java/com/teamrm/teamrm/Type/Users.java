package com.teamrm.teamrm.Type;

/**
 * Created byאOorya on 07/07/2016.
 */
public abstract class Users
{
    public static final String STATUS_ADMIN = "Admin";
    public static final String STATUS_TECH = "Tech";
    public static final String STATUS_CLIENT = "Client";
    private String userID; //hold userID
    private String userNameString;  //hold userNameString
    private String userEmail;
    private boolean userIsAdmin;
    private String userStatus;
    private String userPhone;
    private String userAddress;
    private String userCompany;


    public Users(){}  //empty constructor, must have

    public Users(String userID, String userNameString, String userEmail) {
        this.userID = userID;
        this.userNameString = userNameString;
        this.userEmail = userEmail;
        this.userStatus = STATUS_CLIENT;
        this.userIsAdmin = false;
    }

    public Users(String userID, String userNameString, String userEmail, String userPhone, String userAddress) {
        this.userID = userID;
        this.userNameString = userNameString;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userStatus = STATUS_TECH;
        this.userIsAdmin = false;
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

    public boolean isUserIsAdmin() {
        return userIsAdmin;
    }

    public void setUserIsAdmin(boolean userIsAdmin) {
        this.userIsAdmin = userIsAdmin;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
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
