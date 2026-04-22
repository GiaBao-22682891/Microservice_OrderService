package org.example.nhom2_orderservice.services;

import org.example.nhom2_orderservice.dto.requests.OrderCreateRequest;
import org.example.nhom2_orderservice.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(String userId, OrderCreateRequest request);
    List<Order> getAllOrders();
    List<Order> getMyOrders(String userId);
}
