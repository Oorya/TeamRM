package com.teamrm.teamrm.Type;

import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Client extends Users
{
    public Client(){}

    private List<HashMap> clientCompanies;

    public Client(String userID, String userNameString, String userEmail) {
        super(userID, userNameString, userEmail);
        clientCompanies = UtlFirebase.getAllClientCompanies(userID);
    }

}
