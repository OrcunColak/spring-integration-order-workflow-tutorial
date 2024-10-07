package com.colak.springtutorial.service;


import com.colak.springtutorial.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderValidationService {

    @Transformer
    public Order validateOrder(Order order) {
        log.info("Validating order: {}", order.getOrderId());
        order.setIsValid(order.getOrderId() != null);
        return order;
    }
}
