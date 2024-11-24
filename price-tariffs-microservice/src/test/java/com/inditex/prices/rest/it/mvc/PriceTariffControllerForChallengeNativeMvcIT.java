package com.inditex.prices.rest.it.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.prices.config.TestAppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestPropertySource(properties = {"priceTariffFinderService=native"})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestAppConfig.class})
@WebAppConfiguration
public class PriceTariffControllerForChallengeNativeMvcIT extends PriceTariffControllerForChallengeMvcIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("priceTariffController"));
    }

    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test1() throws Exception {
        super.findPriceTariff_test1();
    }

    @Test
    @DisplayName("Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test2() throws Exception {
        super.findPriceTariff_test2();
    }

    @Test
    @DisplayName("Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test3() throws Exception {
        super.findPriceTariff_test3();
    }

    @Test
    @DisplayName("Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test4() throws Exception {
        super.findPriceTariff_test4();
    }

    @Test
    @DisplayName("Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)")
    public void findPriceTariff_test5() throws Exception {
        super.findPriceTariff_test5();
    }

    @Override
    protected MockMvc getMockMvc() {
        return this.mockMvc;
    }

    @Override
    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
}
