package com.store.ecommerce.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Store E-Commerce API")
                        .version("1.0.0")
                        .description("REST API for North American E-Commerce Backend System")
                        .contact(new Contact()
                                .name("Store Backend Team")));
    }
}
