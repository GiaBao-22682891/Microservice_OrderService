package org.example.nhom2_orderservice.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long id;
    private String name;
    private Double price;
}
