package com.DeliveryFood.lib.Model;

import com.example.lib.Entities.Category;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryModel implements Serializable {
    private boolean status;
    private List<Category> data;
}
