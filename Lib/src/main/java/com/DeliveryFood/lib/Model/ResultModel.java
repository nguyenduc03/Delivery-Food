package com.DeliveryFood.lib.Model;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResultModel implements Serializable {
    private boolean status;
    private String message;
}
