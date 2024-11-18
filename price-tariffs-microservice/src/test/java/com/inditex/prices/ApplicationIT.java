package com.inditex.prices;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIT {
    @Test
    @DisplayName("The application starts")
    public void startApplication() {
        Application.main(null);
    }
}
