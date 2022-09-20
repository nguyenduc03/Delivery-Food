package com.DeliveryFood.lib.Entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Cart {
    private int ID_Food;
    private Float Total_Money;
    private int Quantity;
    private String SDT;
}
