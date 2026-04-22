package org.example.nhom2_orderservice.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.nhom2_orderservice.models.enumerate.Status;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreateRequest {
    List<OrderItemCreateRequest> items;
}
