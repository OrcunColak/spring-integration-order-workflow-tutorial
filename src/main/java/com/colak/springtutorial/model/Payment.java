package com.colak.springtutorial.model;

import com.colak.springtutorial.model.enumerations.PaymentMethod;
import lombok.Data;

@Data
public class Payment {

    private PaymentMethod paymentMethod;

    private String cardNumber;

    private String expirationDate;

    private String cvv;
}
