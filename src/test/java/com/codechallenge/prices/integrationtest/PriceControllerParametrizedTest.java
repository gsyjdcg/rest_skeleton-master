package com.codechallenge.prices.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.codechallenge.Application;
import com.codechallenge.prices.PriceController;
import com.codechallenge.prices.bo.external.GetPriceResponse;
import com.codechallenge.prices.exception.PriceNotFoundException;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
        classes = Application.class)
@TestPropertySource(
    locations = "classpath:application-integration-test.properties")
class PriceControllerParametrizedTest {

    private static final String PRODUCT_ID = "35457";
    private static final long BRAND_ID = 1L;

    @Autowired
    PriceController priceController;

    @ParameterizedTest(name = "test que evalua el precio {0} del artículo en la fecha {1}")
    @CsvFileSource(resources = "/testdata/controllertest.csv", numLinesToSkip = 1)
    void test1(String price, String date) throws PriceNotFoundException {

        ResponseEntity<GetPriceResponse> result = priceController.getPrice(date, PRODUCT_ID, BRAND_ID);
        String finalPrice = Objects.requireNonNull(result.getBody()).getFinalPrice();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(price, finalPrice);
    }

    @Test
    @DisplayName("test que comprueba el error cuando no encuentra precio para un artículo")
    void test6(){
        assertThrows(PriceNotFoundException.class, () -> {
            priceController.getPrice("2021-01-01-00:00:00", PRODUCT_ID, BRAND_ID);
        });
    }

}
