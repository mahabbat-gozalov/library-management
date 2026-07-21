package com.mg_devjoint_task_one.library_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI libraryManagementOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Library Management")
                        .description("REST API for managing books, authors, categories, members, and loans")
                        .version("1.0.0")
                );
    }

}
