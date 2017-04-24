package com.teamrm.teamrm.Type;

import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

import java.util.List;

/**
 * Created by Oorya on 10/08/2016.
 */
public class PendingTech extends Users implements GenericKeyValueTypeable
{
    public static final String ENROLLMENT_CODE_ID = "enrollmentCodeID";

    private String enrollmentCodeID;

    public PendingTech(){}

    public PendingTech(Users user, String _enrollmentCodeID){
        super(user);
        this.enrollmentCodeID = _enrollmentCodeID;
    }

    public String getEnrollmentCodeID() {
        return enrollmentCodeID;
    }

    public void setEnrollmentCodeID(String enrollmentCodeID) {
        this.enrollmentCodeID = enrollmentCodeID;
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

    @Override
    public String toString() {
        String out = ENROLLMENT_CODE_ID + " = " + this.enrollmentCodeID + "\n";
        return super.toString() + out ;
    }
}
