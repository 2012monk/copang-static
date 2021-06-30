package com.example.copangstatic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class CopangStaticApplication {

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
        + "classpath:application.yml,"
        + "classpath:aws.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(CopangStaticApplication.class)
            .properties(APPLICATION_LOCATIONS)
            .run(args);
    }
//    public static void main(String[] args) {
//        SpringApplication.run(CopangStaticApplication.class, args);
//    }

}
