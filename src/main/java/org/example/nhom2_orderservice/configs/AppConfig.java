package org.example.nhom2_orderservice.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@AllArgsConstructor
public class AppConfig {
    private final RestTemplateAuthInterceptor authInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Gắn "trạm kiểm duyệt" vào RestTemplate
        restTemplate.setInterceptors(Collections.singletonList(authInterceptor));

        return restTemplate;
    }
}
