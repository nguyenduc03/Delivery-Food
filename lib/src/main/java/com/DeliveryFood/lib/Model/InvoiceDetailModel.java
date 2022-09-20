package com.DeliveryFood.lib.Model;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class InvoiceDetailModel {
    private boolean status;
    private List<obj> data;
    @Data
    @ToString
    public class obj {
        private int iD_invoice;
        private int iD_Food;
        private int quantity;
        private Float unitPrice;
        private Float amount;
    }
}
