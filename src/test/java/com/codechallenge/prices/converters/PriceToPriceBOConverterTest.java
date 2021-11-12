package com.codechallenge.prices.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.codechallenge.adapter.persistence.entity.Brand;
import com.codechallenge.adapter.persistence.entity.Price;
import com.codechallenge.adapter.persistence.entity.Product;
import com.codechallenge.prices.bo.internal.PriceBO;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceToPriceBOConverterTest {

    public static final long ID_BRAND = 1L;
    public static final String NAME_BRAND = "aname";
    public static final long ID_PRICE = 2L;
    public static final float VALUE_PRICE = 30f;
    public static final int LIST_PRICE = 1;
    public static final String CURRENCY_PRICE = "EUR";
    public static final int PRIORITY_PRICE = 1;
    public static final String ID_PRODUCT = "3";
    public static final String NAME_PRODUCT = "aproductname";
    private PriceToPriceBOConverter priceToPriceBOConverter;

    @BeforeEach
    public void init(){
        priceToPriceBOConverter = new PriceToPriceBOConverter();
    }

    @Test
    @DisplayName("test que verifica la correcta conversión entre pojos")
    @Disabled
    void testConversion(){
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime beginDate = endDate.minusDays(1L);
        PriceBO converted = priceToPriceBOConverter.convert(
                Price.builder()
                .brand(Brand.builder()
                        .id(ID_BRAND)
                        .name(NAME_BRAND)
                        .build())
                .id(ID_PRICE)
                .endDate(endDate)
                .startDate(beginDate)
                .price(VALUE_PRICE)
                .priceList(LIST_PRICE)
                .isoCurrency(CURRENCY_PRICE)
                .priority(PRIORITY_PRICE)
                .product(Product.builder()
                        .id(ID_PRODUCT)
                        .name(NAME_PRODUCT)
                        .build())
                .build());

        assertNotNull(converted);
        assertEquals(converted.getBrandId(), Long.valueOf(ID_BRAND));
        assertEquals(converted.getProductId(), ID_PRODUCT);
        assertEquals(converted.getEndDate(), endDate);
        assertEquals(converted.getStartDate(), beginDate);
        assertEquals(converted.getFinalPrice(), Float.valueOf(VALUE_PRICE));
        assertEquals(converted.getIsoCurrency(), CURRENCY_PRICE);
        assertEquals(converted.getPriceList(), Integer.valueOf(LIST_PRICE));
    }
}
