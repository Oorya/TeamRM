package com.teamrm.teamrm.Type;

/**
 * Created by אוריה on 07/07/2016.
 */
public abstract class Users
{
    public String userName;  //hold userName
    public String email;
    public String userId; //hold userId
    public boolean isAdmin;
    public String status;
    public String address;
    public String phone;

    public Users(){}  //empty constructor, must have

    public Users(String userName, String email, String status, String address, String phone, String userId) {
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.isAdmin = false;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}
