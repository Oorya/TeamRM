package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Product
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
}
