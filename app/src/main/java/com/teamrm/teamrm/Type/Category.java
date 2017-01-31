package com.teamrm.teamrm.Type;

import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Category implements GenericKeyValueTypeable
{
    private String categoryID, categoryName;

    public Category(){}

    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String toString()
    {
        return categoryName;
    }

    @Override
    public String getItemKey() {
        return this.categoryID;
    }

    @Override
    public String getItemValue() {
        return this.categoryName;
    }
}
