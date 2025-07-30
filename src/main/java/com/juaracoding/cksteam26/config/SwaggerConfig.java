package com.juaracoding.cksteam26.config;

/*
@Author satriadega a.k.a. spn
Java Developer
Created on 30/07/25 21.05
@Last Modified 30/07/25 21.05
Version 1.0
*/

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
                .info(new Info()
                        .title("Springboot+JPA+JWT+SQLServer")
                        .description("SPRINGBOOT REST API")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Arsipku")
                                .email("satria.pandega@gmail.com")
                                .url("localhost:8081"))
                        .license(new License()
                                .name("Springboot Open Source License")
                                .url("https://spring.io/")));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }
}


