package org.example.nhom2_orderservice.configs;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RestTemplateAuthInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        // 1. Lấy Request hiện tại mà người dùng đang gọi vào Order Service
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest servletRequest = attributes.getRequest();

            // 2. Trích xuất header "Authorization" (chứa Bearer token)
            String authHeader = servletRequest.getHeader("Authorization");

            // 3. Nếu có token, nhét nó vào request chuẩn bị gửi đi sang service khác
            if (authHeader != null) {
                request.getHeaders().add("Authorization", authHeader);
            }
        }

        // Cho phép request tiếp tục đi tới đích
        return execution.execute(request, body);
    }
}