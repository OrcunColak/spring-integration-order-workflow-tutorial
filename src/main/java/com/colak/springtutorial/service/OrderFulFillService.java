package com.colak.springtutorial.service;

import com.colak.springtutorial.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderFulFillService {

    public String fulfillOrder(Order order) {
        return "Order Fulfilled for the orderId: " + order.getOrderId();
    }
}
