package com.Alejandro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(
                                "http://localhost:8081",
                                "https://localhost:8443",
                                "https://localhost/")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}