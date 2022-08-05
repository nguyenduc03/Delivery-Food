package com.DeliveryFood.lib.Model;

public class Food {

    private String iD_Food;
    private String iD_Category;
    private String name_Food;
    private String dateAdd;
    private String description;
    private String picture;
    private float price;
    private boolean available;


    // Getter Methods

    public String getID_Food() {
        return iD_Food;
    }

    public String getID_Category() {
        return iD_Category;
    }

    public String getName_Food() {
        return name_Food;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public String getDescription() {
        return description;
    }

    public String getPicture() {
        return picture;
    }

    public float getPrice() {
        return price;
    }

    public boolean getAvailable() {
        return available;
    }

    // Setter Methods

    public void setID_Food(String iD_Food) {
        this.iD_Food = iD_Food;
    }

    public void setID_Category(String iD_Category) {
        this.iD_Category = iD_Category;
    }

    public void setName_Food(String name_Food) {
        this.name_Food = name_Food;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
