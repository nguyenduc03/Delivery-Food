package com.DeliveryFood.lib.Interface;


import com.DeliveryFood.lib.Entities.Food;
import com.DeliveryFood.lib.Model.FoodModel;

public interface IChuyenData {
    public void ChuyenData(FoodModel.Data product);
    public void PassDetailBill(Food bill);
    public  void  ChuyenTongTien(float Tien);
}
