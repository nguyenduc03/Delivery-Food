package com.DeliveryFood.lib.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FoodModel {

    private String status;
    public List<Data> data;

    @lombok.Data
    public class Data implements Serializable {
        private int iD_Food;
        private int iD_Category;
        private String name_Food;
        private String dateAdd;
        private String description;
        private String picture;
        private int iD_Discount;
        private float price;
        private double rating;
        private boolean Available;

    }
}
