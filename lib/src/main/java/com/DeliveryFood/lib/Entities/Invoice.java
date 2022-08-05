package com.DeliveryFood.lib.Entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Invoice {
    private String iD_invoice;
    private String iD_Discount;
    private String sdt;
    private Float total_Money;
    private String date_Create;
}
