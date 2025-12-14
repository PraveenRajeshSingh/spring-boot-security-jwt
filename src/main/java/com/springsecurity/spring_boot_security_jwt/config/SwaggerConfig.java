package com.springsecurity.spring_boot_security_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public OpenAPI openAPI() {

        return new OpenAPI()
                .info(apiInfo())
                .components(new Components()
                        .addSecuritySchemes("JWT",
                                new SecurityScheme()
                                        .name(AUTHORIZATION_HEADER)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    private Info apiInfo() {
        return new Info()
                .title("User Security : Backend")
                .description("This project is developed by Praveen Singh")
                .version("1.0")
                .termsOfService("Terms of Service")
                .contact(new Contact()
                        .name("Praveen Singh")
                        .email("singhpraveenrajrsh764@gmail.com"))
                .license(new License()
                        .name("License of APIs")
                        .url("API license URL"));
    }
}