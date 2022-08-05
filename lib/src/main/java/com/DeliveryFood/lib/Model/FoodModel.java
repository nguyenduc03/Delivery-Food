package com.DeliveryFood.lib.Model;

import java.io.Serializable;
import java.util.List;

public class FoodModel {

        private String status;
        public List<Data> data ;

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public class Data implements Serializable {
        public String iD_Food ;
        public String iD_Category ;
        public String name_Food ;
        public String dateAdd ;
        public String description ;
        public String picture ;
        public float price ;
        public boolean Available ;



        public String getID_Food() {
            return iD_Food;
        }

        public void setID_Food(String ID_Food) {
            this.iD_Food = ID_Food;
        }

        public String getID_Category() {
            return iD_Category;
        }

        public void setID_Category(String ID_Category) {
            this.iD_Category = ID_Category;
        }

        public String getName_Food() {
            return name_Food;
        }

        public void setName_Food(String name_Food) {
            name_Food = name_Food;
        }

        public String getDateAdd() {
            return dateAdd;
        }

        public void setDateAdd(String dateAdd) {
            dateAdd = dateAdd;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            description = description;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            picture = picture;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            price = price;
        }

        public boolean isAvailable() {
            return Available;
        }

        public void setAvailable(boolean available) {
            Available = available;
        }
    }
}
