package com.mg_devjoint.library_management.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI OpenAPI() {

        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("Enter your JWT token in the format: **Bearer <token>**");

        Info info = new Info()
                .title("🔐 Library Management API")
                .description("""
                        Handles:
                        • User Management API
                        • Login / logout
                        • JWT Access + Refresh token management
                        • Book Management API
                        • Category Management API
                        • Author Management API
                        • Member Management API
                        • Loan Management API
                        """)
                .version("1.0.0")
                .contact(new Contact()
                        .name("Məhəbbət Gözəlov")
                        .email("mgzlovcontact@gmail.com")
                        .url("https://github.com/mahabbat-gozalov"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"));


        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerAuth));


    }
}
