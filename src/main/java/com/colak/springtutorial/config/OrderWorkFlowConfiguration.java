package com.colak.springtutorial.config;

import com.colak.springtutorial.model.Order;
import com.colak.springtutorial.service.NotificationService;
import com.colak.springtutorial.service.OrderEnrichmentService;
import com.colak.springtutorial.service.OrderFulFillService;
import com.colak.springtutorial.service.OrderValidationService;
import com.colak.springtutorial.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageHandler;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OrderWorkFlowConfiguration {


    private final OrderValidationService orderValidationService;

    private final OrderEnrichmentService orderEnrichmentService;

    private final PaymentService paymentService;

    private final NotificationService notificationService;

    private final OrderFulFillService orderFulFillService;

    /**
     * Order processing flow
     * 1. Order validation
     * 2. Enrich order
     * 3. Payment processing
     * 4. Notification
     * 5. Order fulfillment
     */
    @Bean
    public IntegrationFlow orderValidationFlow() {
        return IntegrationFlow.from("orderChannel")
                .transform(order -> orderValidationService.validateOrder((Order) order))
                .<Order, Boolean>route(Order::getIsValid, mapping -> mapping
                        .subFlowMapping(true, sf -> sf.channel("enrichOrderChannel"))
                        .subFlowMapping(false, sf -> sf.transform(order -> {
                            throw new RuntimeException("Invalid order");
                        }))
                )
                .get();
    }

    @Bean
    public IntegrationFlow enrichOrderFlow() {
        return IntegrationFlow.from("enrichOrderChannel")
                .transform(order -> orderEnrichmentService.enrichOrder((Order) order))
                .channel("paymentChannel")
                .get();
    }

    @Bean
    public IntegrationFlow paymentProcessingFlow() {
        return IntegrationFlow.from("paymentChannel")
                .transform(order -> paymentService.processPayment((Order) order))
                .publishSubscribeChannel(c -> c
                        .subscribe(s -> s.channel("notificationChannel"))
                        .subscribe(s -> s.channel("orderFulfillmentChannel"))
                )
                .get();
    }

    @Bean
    public IntegrationFlow notificationFlow() {
        return IntegrationFlow.from("notificationChannel")
                .transform(order -> notificationService.sendNotification((Order) order))
                .handle(orderCompletionHandler())
                .get();
    }

    @Bean
    public IntegrationFlow orderFulfillmentFlow() {
        return IntegrationFlow.from("orderFulfillmentChannel")
                .transform(order -> orderFulFillService.fulfillOrder((Order) order))
                .handle(orderCompletionHandler())
                .get();
    }

    @Bean
    public MessageHandler orderCompletionHandler() {
        return message -> log.info("Order fulfilled: {}", message);
    }
}
