package com.assignment.kalahagamedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.assignment.kalahagamedemo.configuration.properties")
public class KalahaGameDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KalahaGameDemoApplication.class, args);
    }

}
