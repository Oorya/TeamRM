package com.teamrm.teamrm.Type;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

/**
 * Created by Oorya on 10/08/2016.
 */
public class Client extends Users implements GenericKeyValueTypeable {

    public Client() {
    }

    public Client(String clientID, String userNameString, String userEmail, @Nullable String userPhone, @Nullable String clientRegion, @Nullable String userAddress) {
        super(clientID, userNameString, userEmail);
    }

    @Override
    @Exclude
    public String getItemKey() {
        return super.getUserID();
    }

    @Override
    @Exclude
    public String getItemValue() {
        return super.getUserNameString();
    }

}
