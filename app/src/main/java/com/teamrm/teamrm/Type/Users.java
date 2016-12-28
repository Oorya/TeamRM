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


    public static Users getCorentUser()
    {
        Users corentUser = null;//מתודה שמושכת את היוזר פרופייל מהפיירבייס ע"י כתובת מייל
        return corentUser;
    }

    public Users(){}  //empty constructor, must have

    public Users(String userName, String email, String address, String phone, String userId,String status) {
        this.userName = userName;
        this.email = email;
        this.userId = userId;
        this.status = "Client";
        this.address = address;
        this.phone = phone;
        this.isAdmin = false;
        this.company = "";
        this.status = status;
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
