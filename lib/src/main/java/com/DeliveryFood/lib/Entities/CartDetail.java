package com.DeliveryFood.lib.Entities;

import com.DeliveryFood.lib.Model.FoodModel;
import com.DeliveryFood.lib.Model.ToppingModel;

import java.util.List;

import lombok.Data;

@Data
public class CartDetail {
    private FoodModel.Data Food;
    private List<ToppingModel.Data> toppings;
    private Float Total_Money;
    private int Quantity;
}
