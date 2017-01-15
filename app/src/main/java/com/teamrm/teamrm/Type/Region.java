package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Region
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
}
