package com.teamrm.teamrm.Type;

/**
 * Created byא on 07/07/2016.
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
    public String company;

    public Users(){}  //empty constructor, must have

    public Users(String userName, String email, String address, String phone, String userId) {
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.status = "Client";
        this.address = address;
        this.phone = phone;
        this.isAdmin = false;
        this.company = "";
    }
}
