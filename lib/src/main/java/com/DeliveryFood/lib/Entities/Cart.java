package com.DeliveryFood.lib.Entities;

import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ToppingModel;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Cart {
    private FoodModel.Data Food;
    int ID_Food;
    private List<ToppingModel.Data> toppings;
    private Float Total_Money;
    private int Quantity;
    String SDT;
}
