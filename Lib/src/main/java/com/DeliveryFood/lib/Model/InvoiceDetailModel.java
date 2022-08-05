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
        private String iD_invoice;
        private String iD_Food;
        private int quantity;
        private Float unitPrice;
        private Float amount;
    }
}
