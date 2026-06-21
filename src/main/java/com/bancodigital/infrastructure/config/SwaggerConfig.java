package com.bancodigital.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema Bancário Digital")
                        .description("API REST para gerenciamento de contas bancárias")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Alexsandro Baliero")
                                .url("https://github.com/AlexsandroBaliero")));
    }
}