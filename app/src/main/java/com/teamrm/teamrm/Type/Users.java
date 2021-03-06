package com.teamrm.teamrm.Type;

import java.util.Date;

/**
 * Created byאOorya on 07/07/2016.
 */
public abstract class Users {
    public static final String STATUS_ADMIN = "Admin";
    public static final String STATUS_TECH = "Technician";
    public static final String STATUS_PENDING_TECH = "PendingTech";
    public static final String STATUS_CLIENT = "Client";

    private String userID;
    private String userNameString;
    private String userEmail;
    private boolean userIsAdmin;
    private String userStatus;
    private String userPhone;
    private String userAddress;
    private String userImgPath;
    private String assignedCompanyID;
    private String userLastSeen;

    public static final String USER_ID = "userID";
    public static final String USER_NAME_STRING = "userNameString";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_IS_ADMIN = "userIsAdmin";
    public static final String USER_STATUS = "userStatus";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_ADDRESS = "userAddress";
    public static final String USER_IMG_PATH = "userImgPath";
    public static final String ASSIGNED_COMPANY_ID = "assignedCompanyID";
    public static final String USER_LAST_SEEN = "userLastSeen";


    public Users() {
    }  //empty constructor, must have

    public Users(String userID, String userNameString, String userEmail) {
        this.userID = userID;
        this.userNameString = userNameString;
        this.userEmail = userEmail;
        this.userStatus = STATUS_CLIENT;
        this.userIsAdmin = false;

    }

    public Users(Users otherUser) {
        this.userID = otherUser.userID;
        this.userNameString = otherUser.userNameString;
        this.userEmail = otherUser.userEmail;
        this.userIsAdmin = otherUser.userIsAdmin;
        this.userStatus = otherUser.userStatus;
        this.userPhone = otherUser.userPhone;
        this.userAddress = otherUser.userAddress;
        this.userImgPath = otherUser.userImgPath;
        this.assignedCompanyID = otherUser.assignedCompanyID;
        this.userLastSeen = otherUser.userLastSeen;

    }

    public String getUserLastSeen() {
        return userLastSeen;
    }

    public void setUserLastSeen(String userLastSeen) {
        this.userLastSeen = userLastSeen;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getAssignedCompanyID() {
        return assignedCompanyID;
    }

    public void setAssignedCompanyID(String assignedCompanyID) {
        this.assignedCompanyID = assignedCompanyID;
    }

    @Override
    public String toString() {
        String out = "userID = " + getUserID() + "\n";
        out += "userNameString = " + getUserNameString() + "\n";
        out += "userEmail = " + getUserEmail() + "\n";
        out += "userStatus = " + getUserStatus() + "\n";
        return out;

    }
}
