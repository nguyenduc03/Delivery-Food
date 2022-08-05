package com.DeliveryFood.lib.Model;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Account implements Serializable {
    private boolean status;
    public Data data;
    @lombok.Data
    @ToString
    public class Data{
        private String sdt;
        private String password;
        private String address;
        private String avatar;
        private String name;
    }

}

