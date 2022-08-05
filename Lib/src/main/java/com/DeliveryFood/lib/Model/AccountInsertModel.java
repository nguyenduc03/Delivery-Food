package com.DeliveryFood.lib.Model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class AccountInsertModel {
    private String SDT;
    private String Password;
    private String Address;
    private String Avatar;
    private String Name;
}
