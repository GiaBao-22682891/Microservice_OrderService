package org.example.nhom2_orderservice.controllers;

import lombok.AllArgsConstructor;
import org.example.nhom2_orderservice.dto.requests.OrderCreateRequest;
import org.example.nhom2_orderservice.models.Order;
import org.example.nhom2_orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderControllers {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(
            @RequestBody OrderCreateRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(
                orderService.createOrder(jwt.getClaimAsString("userId"), request));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders(
            @AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getClaimAsString("userId");
        return ResponseEntity.ok(
                orderService.getMyOrders(userId));
    }
}
