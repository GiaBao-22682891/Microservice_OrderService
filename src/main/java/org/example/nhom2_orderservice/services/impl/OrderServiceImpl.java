package org.example.nhom2_orderservice.services.impl;

import org.example.nhom2_orderservice.dto.OrderItemDTO;
import org.example.nhom2_orderservice.models.Order;
import org.example.nhom2_orderservice.models.OrderItem;
import org.example.nhom2_orderservice.models.enumerate.Status;
import org.example.nhom2_orderservice.repositories.OrderRepository;
import org.example.nhom2_orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Order createOrder(Order order) {
        // 1. Validate User (Gọi sang Người số 2 - Cổng 8081) [cite: 43, 68, 81]
        String userURL = "http://172.16.53.194/api/auth/register/" + order.getUserId();
        try {
            restTemplate.getForObject(userURL, Object.class);
        } catch (Exception e) {
            throw new RuntimeException("User không tồn tại hoặc lỗi kết nối mạng LAN!");
        }

        // 2. Xử lý danh sách món ăn (Gọi sang Người số 3 - Cổng 8082) [cite: 51, 67, 83]
        double totalOrderPrice = 0;

        for (OrderItem item : order.getOrderItems()) {
            // URL lấy chi tiết 1 món ăn: /api/foods/{id}
            String foodUrl = "http://172.16.52.161:8082/api/foods/" + item.getFoodId();

            // Gọi Food Service để lấy thông tin món (để lấy giá chuẩn nhất từ DB của họ)
            OrderItemDTO food = restTemplate.getForObject(foodUrl, OrderItemDTO.class);

            if (food != null) {
                item.setPrice(food.getPrice()); // Gán giá hiện tại vào hóa đơn
                item.setOrder(order); // Liên kết item với order (để JPA lưu đúng quan hệ 1-n)
                totalOrderPrice += food.getPrice() * item.getQuantity();
            }
        }

        // 3. Hoàn tất đơn hàng
        order.setTotalPrice(totalOrderPrice);
        order.setStatus(Status.PENDING); // Trạng thái mặc định khi mới tạo [cite: 22]

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
