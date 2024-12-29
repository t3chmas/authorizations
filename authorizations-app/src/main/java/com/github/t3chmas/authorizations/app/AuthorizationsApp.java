package com.github.hasable.authorizations.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages =
    "com.github.hasable.authorizations")
public class AuthorizationsApp {

    public static void main(String... args) {
        SpringApplication.run(AuthorizationsApp.class, args);
    }
}
