package com.teamrm.teamrm.Type;

/**
 * Created by oorya on 10/08/2016.
 */
public class Admin extends Users {

    public Admin(){}

    public Admin(String userName, String userPass, String email, String status, String address, String phone, String userID) {
        super(userName, userPass, email, status, address, phone, userID);
    }
}
