package com.inditex.prices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.inditex.prices"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
