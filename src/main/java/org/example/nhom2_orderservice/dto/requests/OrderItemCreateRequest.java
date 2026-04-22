package org.example.nhom2_orderservice.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemCreateRequest {
    long foodId;
    int quantity;
}
