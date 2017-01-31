package com.teamrm.teamrm.Type;

import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Client extends Users implements GenericKeyValueTypeable
{
    public Client(){}

    private List<HashMap> clientCompanies;

    public Client(String userID, String userNameString, String userEmail) {
        super(userID, userNameString, userEmail);
        clientCompanies = UtlFirebase.getAllClientCompanies(userID);
    }

    @Override
    public String getItemKey() {
        return super.getUserID();
    }

    @Override
    public String getItemValue() {
        return super.getUserNameString();
    }
}
