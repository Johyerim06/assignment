package com.example.extblocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.extblocker.repo")
public class ExtBlockerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExtBlockerApplication.class, args);
    }
}
