package com.teamrm.teamrm.Type;

/**
 * Created byאOorya on 07/07/2016.
 */
public abstract class Users
{
    private String userName;  //hold userName
    private String email;
    private String userId; //hold userId
    private boolean isAdmin;
    private String status;
    private String address;
    private String phone;
    private String company;

    public Users(){}  //empty constructor, must have

    public Users(String userName, String email, String userId) {
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.status = "Client";
        this.isAdmin = false;
        this.company = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
