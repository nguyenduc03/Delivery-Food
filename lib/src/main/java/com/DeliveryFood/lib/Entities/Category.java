package com.DeliveryFood.lib.Entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Category {
    private int iD_Category;
    private String name_Category;
    private String description;
    private String picture;
}
