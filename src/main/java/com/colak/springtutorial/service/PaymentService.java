package com.colak.springtutorial.service;

import com.colak.springtutorial.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    public Order processPayment(Order order) {
        log.info("Processing payment for the order {}", order.getOrderId());
        return order;// Assume the payment is accepted
    }
}
