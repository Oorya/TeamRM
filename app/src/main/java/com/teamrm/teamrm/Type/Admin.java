package com.teamrm.teamrm.Type;

/**
 * Created by oorya on 10/08/2016.
 */
public class Admin extends Users {

    public Admin(){}

    public Admin(String userName, String email, String status, String address, String phone, String userID) {
        super(userName, email, status, address, phone, userID);
    }
}
