package com.colak.springtutorial.service;

import com.colak.springtutorial.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public String sendNotification(Order order) {
        log.info("Sending notification for the order: {}", order.getOrderId());
        return "Notification Sent for the order: " + order.getOrderId();
    }
}