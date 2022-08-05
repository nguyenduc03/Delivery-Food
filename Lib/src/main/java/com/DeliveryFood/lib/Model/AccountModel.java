package com.DeliveryFood.lib.Model;

import java.io.Serializable;

public class AccountModel implements Serializable {
    private boolean status;
    private String message;

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
