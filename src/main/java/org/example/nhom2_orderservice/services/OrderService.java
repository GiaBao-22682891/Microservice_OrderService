package org.example.nhom2_orderservice.services;

import org.example.nhom2_orderservice.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder (Order order);
    List<Order> getAllOrders();

}
