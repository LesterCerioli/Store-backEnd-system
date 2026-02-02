package com.store.ecommerce;

import com.store.ecommerce.application.config.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AuthProperties.class)
public class StoreBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreBackendApplication.class, args);
    }
}
