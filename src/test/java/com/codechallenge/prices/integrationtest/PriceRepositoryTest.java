package com.codechallenge.prices.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codechallenge.adapter.persistence.entity.Price;
import com.codechallenge.adapter.persistence.entity.Product;
import com.codechallenge.adapter.persistence.repository.PriceRepository;
import com.codechallenge.Application;
import com.codechallenge.prices.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(
        classes = Application.class)
@TestPropertySource(
    locations = "classpath:application-integration-test.properties")
@Sql(value = {"/sql/more-data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/delete-more-data.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class PriceRepositoryTest {

    private static final String PRODUCT_ID = "35458";
    private static final long BRAND_ID = 1L;
    
    @Autowired
    PriceRepository priceRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");

    @Test
    @DisplayName("test que evalua el precio standard en el año del articulo")
    void test1() throws PriceNotFoundException{
        List<Price> result = getPrices("2021-06-14-10:00:00");

        assertEquals(1, result.size());
        assertEquals(Float.valueOf(35.5f), result.get(0).getPrice());
    }

    @Test
    @DisplayName("test que evalua el precio rebajado del artículo el 16 junio")
    void test2() throws PriceNotFoundException{
        List<Price> result = getPrices("2021-06-14-16:00:00");

        assertEquals(2, result.size());
        assertEquals(Float.valueOf(25.45f), result.get(0).getPrice());
    }

    @Test
    @DisplayName("test que evalua el precio standar del artículo en junio")
    void test3() throws PriceNotFoundException{
        List<Price> result = getPrices("2021-06-14-21:00:00");

        assertEquals(1, result.size());
        assertEquals(Float.valueOf(35.5f), result.get(0).getPrice());
    }

    @Test
    @DisplayName("test que evalua el precio rebajado del artículo el 15 junio")
    void test4() throws PriceNotFoundException{
        List<Price> result = getPrices("2021-06-15-10:00:00");

        assertEquals(2, result.size());
        assertEquals(Float.valueOf(30.5f), result.get(0).getPrice());
    }

    @Test
    @DisplayName("test que evalua el precio standar del artículo pasado el 16 de junio")
    void test5() throws PriceNotFoundException {
        List<Price> result = getPrices("2021-06-16-21:00:00");

        assertEquals(2, result.size());
        assertEquals(Float.valueOf(38.95f), result.get(0).getPrice());
    }

    @Test
    @DisplayName("test que comprueba el error cuando no encuentra precio para un artículo")
    void test6(){
        assertThrows(PriceNotFoundException.class, () -> {
            getPrices("2024-06-16-21:00:00");
        });
    }

    @Test
    @DisplayName("test que comprueba el funcionamiento correcto de findByProduct")
    void test7(){
        Optional<List<Price>> products = priceRepository
            .findByProductOrderByStartDateAsc(Product.builder()
                .id(PRODUCT_ID)
                .build());

        assertTrue(products.isPresent());
        assertEquals(products.get().size(), 4);
    }

    private List<Price> getPrices(String date) throws PriceNotFoundException {
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        List<Price> elements = priceRepository
            .findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderedByPriorityDesc(
                localDateTime, PRODUCT_ID, BRAND_ID)
            .orElseThrow(PriceNotFoundException::new);

        if (elements.isEmpty()) throw new PriceNotFoundException();

        return elements;
    }

}
