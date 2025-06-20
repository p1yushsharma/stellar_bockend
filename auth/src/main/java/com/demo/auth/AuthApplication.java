package com.demo.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class AuthApplication {
    public static void main(String[] args) {
       
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "sit");

        SpringApplication.run(AuthApplication.class, args);
    }
}
