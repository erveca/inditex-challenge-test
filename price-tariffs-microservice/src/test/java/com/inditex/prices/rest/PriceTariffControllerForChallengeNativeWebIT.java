package com.inditex.prices.rest;

import com.inditex.prices.exception.InvalidBrandException;
import com.inditex.prices.exception.InvalidDateException;
import com.inditex.prices.exception.InvalidProductException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"priceTariffFinderService=native"})
public class PriceTariffControllerForChallengeNativeWebIT extends PriceTariffControllerForChallengeWebIT {
    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test1() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        super.findPriceTariff_test1();
    }

    @Test
    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test2() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        super.findPriceTariff_test2();
    }

    @Test
    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test3() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        super.findPriceTariff_test3();
    }

    @Test
    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test4() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        super.findPriceTariff_test4();
    }

    @Test
    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test5() throws InvalidDateException, InvalidBrandException, InvalidProductException {
        super.findPriceTariff_test5();
    }
}
