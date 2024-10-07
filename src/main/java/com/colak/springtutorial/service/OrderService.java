package com.colak.springtutorial.service;

import com.colak.springtutorial.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final MessageChannel orderChannel;


    /**
     * Process the order
     *
     * @param order Order to process
     */
    public void processOrder(Order order) {
        log.info("Processing order: {}", order.getOrderId());
        orderChannel.send(MessageBuilder.withPayload(order).build());
    }
}
