package com.DeliveryFood.lib.Entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InvoiceInsertModel {
    private String ID_Discount;
    private String SDT;
    private float Total_Money;
}
