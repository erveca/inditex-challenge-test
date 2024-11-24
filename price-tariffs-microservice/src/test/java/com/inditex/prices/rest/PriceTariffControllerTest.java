package com.inditex.prices.rest;

import com.inditex.prices.service.PriceTariffService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PriceTariffControllerTest {
    @Mock
    private PriceTariffService priceTariffService;

    @InjectMocks
    private PriceTariffController priceTariffController;

    // TODO Implement unit tests similarly to the ones in PriceTariffServiceTest
}
