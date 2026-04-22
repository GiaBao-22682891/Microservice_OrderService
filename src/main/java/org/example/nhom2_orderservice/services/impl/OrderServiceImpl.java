package org.example.nhom2_orderservice.services.impl;

import org.example.nhom2_orderservice.dto.OrderItemDTO;
import org.example.nhom2_orderservice.dto.requests.OrderCreateRequest;
import org.example.nhom2_orderservice.dto.requests.OrderItemCreateRequest;
import org.example.nhom2_orderservice.models.Order;
import org.example.nhom2_orderservice.models.OrderItem;
import org.example.nhom2_orderservice.models.enumerate.Status;
import org.example.nhom2_orderservice.repositories.OrderRepository;
import org.example.nhom2_orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.services.food-service.url}")
    private String foodServiceUrl;
    @Override
    public Order createOrder(String userId, OrderCreateRequest request) {
        String contextPath = "/api/foods/";
        double totalOrderPrice = 0;
        Order order = new Order();

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemCreateRequest item : request.getItems()) {
            OrderItem orderItem = new OrderItem();
            String foodUrl = foodServiceUrl + contextPath + item.getFoodId();
            OrderItemDTO food = restTemplate.getForObject(foodUrl, OrderItemDTO.class);

            if (food != null) {
                orderItem.setPrice(food.getPrice());
                orderItem.setFoodId(item.getFoodId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setOrder(order);

                totalOrderPrice += food.getPrice() * item.getQuantity();

                orderItems.add(orderItem);
            }
        }

        order.setUserId(Long.parseLong(userId));
        order.setTotalPrice(totalOrderPrice);
        order.setStatus(Status.PENDING);
        order.setOrderItems(orderItems);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getMyOrders(String userId) {
        return orderRepository.getOrderByUserId(userId);
    }
}
