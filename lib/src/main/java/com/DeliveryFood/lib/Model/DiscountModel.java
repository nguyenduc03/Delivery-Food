package com.DeliveryFood.lib.Model;

import java.util.List;

import lombok.ToString;
@lombok.Data
public class DiscountModel {
    private boolean status;
    public List<Data> data;
    @lombok.Data
    @ToString
    public class Data{
        private int iD_Discount;
        private String name;
        private String date_Start;
        private String date_End;
        private String description;
        private int percent;
        private String code;
    }
}
