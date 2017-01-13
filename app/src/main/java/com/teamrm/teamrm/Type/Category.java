package com.teamrm.teamrm.Type;

/**
 * Created by Oorya on 13/01/2017.
 */

public class Category
{
    private String categoryName;
    private int categoryID;
    private Product product;
    private static int counterID = 1000;

    public Category(){}

    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.categoryID = ++counterID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Product getProduct() {
        return product;
    }

    public int getCategoryID() {
        return categoryID;
    }
}
