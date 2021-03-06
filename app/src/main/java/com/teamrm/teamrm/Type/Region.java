package com.teamrm.teamrm.Type;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Region implements GenericKeyValueTypeable
{
    private String regionID, regionName;

    public Region(){}

    public Region(String regionID, String regionName) {
        this.regionID = regionID;
        this.regionName = regionName;
    }


    public String getRegionName() {
        return regionName;
    }

    public String getRegionID() {
        return regionID;
    }

    @Override
    @Exclude
    public String getItemKey() {
        return this.regionID;
    }

    @Override
    @Exclude
    public String getItemValue() {
        return this.regionName;
    }
}
