package com.DeliveryFood.lib.Entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InvoiceDetail {
    private String iD_invoice;
    private String iD_Food;
    private int quantity;
    private Float unitPrice;
    private Float amount;
}
