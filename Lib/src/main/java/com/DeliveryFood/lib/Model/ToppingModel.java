package com.DeliveryFood.lib.Model;

import java.util.List;

import lombok.ToString;

@lombok.Data
@ToString
public class ToppingModel  {
    private String status;
    public List<Data> data ;

    @lombok.Data
    @ToString
    public class Data {
        private String iD_Topping;
        private String iD_Category;
        private String name_Topping;
        private float price;
        private String img;
    }
}
