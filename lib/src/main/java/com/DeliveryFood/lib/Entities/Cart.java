package com.DeliveryFood.lib.Entities;

import com.DeliveryFood.lib.Model.ToppingModel;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Cart {
    int ID_Food;
    private List<ToppingModel.Data> toppings;
    private int Quantity;
    String SDT;
}
