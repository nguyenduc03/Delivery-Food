package com.DeliveryFood.lib.Model;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InvoiceModel {
    private String status;
    public List<Data> data ;

    @lombok.Data
    @ToString
public class Data{
    private String iD_invoice;
    private String iD_Discount;
    private String sdt;
    private float total_Money;
    private String date_Create;

}
}
