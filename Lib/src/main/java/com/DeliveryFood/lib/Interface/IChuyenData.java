package com.DeliveryFood.lib.Interface;


import com.example.lib.Entities.Food;
import com.example.lib.Model.FoodModel;

public interface IChuyenData {
    public void ChuyenData(FoodModel.Data product);
    public void PassDetailBill(Food bill);
    public  void  ChuyenTongTien(float Tien);
}
