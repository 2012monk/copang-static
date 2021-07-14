package com.example.copangstatic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://127.0.0.1:5500",
                "http://localhost:3000",
                "http://localhost:47788",
                "http://localhost:8080", "https://seller.alconn.co")
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowCredentials(true)
            .maxAge(3600L);
    }

}
