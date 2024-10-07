package com.colak.springtutorial.controller;

import com.colak.springtutorial.model.Address;
import com.colak.springtutorial.model.Order;
import com.colak.springtutorial.model.OrderItem;
import com.colak.springtutorial.model.Payment;
import com.colak.springtutorial.model.enumerations.OrderStatus;
import com.colak.springtutorial.model.enumerations.PaymentMethod;
import com.colak.springtutorial.model.enumerations.ShippingMethod;
import com.colak.springtutorial.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    /**
     * Place an order
     *
     * @param order Order to be placed
     * @return Order
     */
    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        log.info("Received order: {}", order);
        orderService.processOrder(order);
        return ResponseEntity.ok(order);
    }

    // http://localhost:8080/api/v1/orders
    @GetMapping
    public ResponseEntity<Order> placeOrder() {
        Order order = new Order();
        order.setOrderId("12345");
        order.setCustomerId("customer123");
        order.setOrderDate("2023-12-25");

        Address shippingAddress = new Address();
        shippingAddress.setStreetAddress("123 Main Street");
        shippingAddress.setCity("Anytown");
        shippingAddress.setState("CA");
        shippingAddress.setPostalCode("12345");
        shippingAddress.setCountry("USA");

        order.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setStreetAddress("456 Elm Street");
        billingAddress.setCity("Anothertown");
        billingAddress.setState("NY");
        billingAddress.setPostalCode("54321");
        billingAddress.setCountry("USA");

        order.setBillingAddress(billingAddress);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId(101L);
        orderItem1.setProductName("Product A");
        orderItem1.setQuantity(2);
        orderItem1.setPrice(new BigDecimal("19.99"));

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductId(102L);
        orderItem2.setProductName("Product B");
        orderItem2.setQuantity(1);
        orderItem2.setPrice(new BigDecimal("29.99"));

        order.setItems(Set.of(orderItem2));

        Payment payment = new Payment();
        payment.setPaymentMethod(PaymentMethod.CreditCard);
        payment.setCardNumber("1234567890123456");
        payment.setExpirationDate("12/25");
        payment.setCvv("123");

        order.setPayment(payment);

        order.setShippingMethod(ShippingMethod.Standard);
        order.setShippingCost(new BigDecimal("5.99"));
        order.setOrderTotal(new BigDecimal("65.97"));
        order.setOrderStatus(OrderStatus.Pending);


        orderService.processOrder(order);
        return ResponseEntity.ok(order);
    }
}
