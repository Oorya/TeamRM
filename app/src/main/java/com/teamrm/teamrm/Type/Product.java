package com.teamrm.teamrm.Type;

import com.google.firebase.database.Exclude;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Product implements GenericKeyValueTypeable
{
    private String productID, productName;

    public Product(){}

    public Product(String productID, String productName) {
        this.productID = productID;
        this.productName = productName;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String toString()
    {
        return productName;
    }

    @Override
    @Exclude
    public String getItemKey() {
        return this.productID;
    }

    @Override
    @Exclude
    public String getItemValue() {
        return this.productName;
    }
}
