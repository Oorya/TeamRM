package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Product
{
    private String productName;
    private int productID;
    private static int counterID = 1000;

    public Product(){}

    public Product(String productName, int productID) {
        this.productName = productName;
        this.productID = ++counterID;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductID() {
        return productID;
    }
}
