package org.example.nhom2_orderservice.repositories;

import org.example.nhom2_orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order, Long> {
    List<Order> getOrderByUserId(String userId);
}
