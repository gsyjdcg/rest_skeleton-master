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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(
        classes = Application.class)
@TestPropertySource(
    locations = "classpath:application-integration-test.properties")

class PriceControllerTest {

    private static final String PRODUCT_ID = "35457";
    private static final long BRAND_ID = 1L;

    @Autowired
    PriceController priceController;

    @Test
    @DisplayName("test que evalua el precio standard en el año del articulo")
    void test1() throws PriceNotFoundException {

        ResponseEntity<GetPriceResponse> result = priceController.getPrice("2020-06-14-10:00:00", PRODUCT_ID, BRAND_ID);
        String finalPrice = Objects.requireNonNull(result.getBody()).getFinalPrice();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("35.5", finalPrice);
    }

    @Test
    @DisplayName("test que evalua el precio rebajado del artículo el 16 junio")
    void test2() throws PriceNotFoundException{
        ResponseEntity<GetPriceResponse> result = priceController.getPrice("2020-06-14-16:00:00", PRODUCT_ID, BRAND_ID);
        String finalPrice = Objects.requireNonNull(result.getBody()).getFinalPrice();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("25.45", finalPrice);

    }

    @Test
    @DisplayName("test que evalua el precio standar del artículo en junio")
    void test3() throws PriceNotFoundException{
        ResponseEntity<GetPriceResponse> result = priceController.getPrice("2020-06-14-21:00:00", PRODUCT_ID, BRAND_ID);
        String finalPrice = Objects.requireNonNull(result.getBody()).getFinalPrice();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("35.5", finalPrice);
    }

    @Test
    @DisplayName("test que evalua el precio rebajado del artículo el 15 junio")
    void test4() throws PriceNotFoundException{
        ResponseEntity<GetPriceResponse> result = priceController.getPrice("2020-06-15-00:00:00", PRODUCT_ID, BRAND_ID);
        String finalPrice = Objects.requireNonNull(result.getBody()).getFinalPrice();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("30.5", finalPrice);
    }

    @Test
    @DisplayName("test que evalua el precio standar del artículo pasado el 16 de junio")
    void test5() throws PriceNotFoundException{
        ResponseEntity<GetPriceResponse> result = priceController.getPrice("2020-06-16-21:00:00", PRODUCT_ID, BRAND_ID);
        String finalPrice = Objects.requireNonNull(result.getBody()).getFinalPrice();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("38.95", finalPrice);

    }

    @Test
    @DisplayName("test que comprueba el error cuando no encuentra precio para un artículo")
    void test6(){
        assertThrows(PriceNotFoundException.class, () -> {
            priceController.getPrice("2021-01-01-00:00:00", PRODUCT_ID, BRAND_ID);
        });
    }

}
