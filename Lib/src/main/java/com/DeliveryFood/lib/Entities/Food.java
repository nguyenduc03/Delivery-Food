package com.DeliveryFood.lib.Entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Food {
    private String status;
    public Data data ;

    public  class Data{
        public String iD_Food;
        private String iD_Category;
        private String name_Food;
        private String dateAdd;
        private String description;
        private String picture;
        private float price;
        private boolean available;

        public String getiD_Food() {
            return iD_Food;
        }

        public void setiD_Food(String iD_Food) {
            this.iD_Food = iD_Food;
        }

        public String getiD_Category() {
            return iD_Category;
        }

        public void setiD_Category(String iD_Category) {
            this.iD_Category = iD_Category;
        }

        public String getName_Food() {
            return name_Food;
        }

        public void setName_Food(String name_Food) {
            this.name_Food = name_Food;
        }

        public String getDateAdd() {
            return dateAdd;
        }

        public void setDateAdd(String dateAdd) {
            this.dateAdd = dateAdd;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }

}
