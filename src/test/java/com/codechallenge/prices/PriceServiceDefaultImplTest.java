package com.codechallenge.prices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codechallenge.adapter.persistence.entity.Price;
import com.codechallenge.adapter.persistence.entity.Product;
import com.codechallenge.adapter.persistence.repository.PriceRepository;
import com.codechallenge.common.util.LocalDateTimeConversionUtils;
import com.codechallenge.prices.bo.internal.GetPriceBO;
import com.codechallenge.prices.bo.internal.PriceBO;
import com.codechallenge.prices.exception.PriceNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

@ExtendWith(MockitoExtension.class)
class PriceServiceDefaultImplTest {

    public static final String PRODUCT_ID = "343223";
    public static final long BRAND_ID = 1;
    public static final long PRICE_ID = 34511;
    public static final String A_STRING_DATE = "2020-07-10-10:00:00";
    public static final String PATTERN_STRING_TO_LOCAL_DATE_TIME = "yyyy-MM-dd-HH:mm:ss";

    private PriceService sut;

    private final LocalDateTimeConversionUtils localDateTimeConversionUtils =
        new LocalDateTimeConversionUtils(PATTERN_STRING_TO_LOCAL_DATE_TIME);

    @Mock
    private PriceRepository priceRepositoryMock;

    @Mock
    private ConversionService conversionServiceMock;

    @Captor
    ArgumentCaptor<Object> argCaptor;

    @BeforeEach
    public void before(){
        sut = new PriceServiceDefaultImpl(priceRepositoryMock, conversionServiceMock);
    }

    @Test
    void givenValidGetPriceBOTheConversionIsCorrect() throws PriceNotFoundException {
        GetPriceBO getPriceBOExample = GetPriceBO.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .ldtApplicationDate(getValidLocalAppDate())
                .build();

        List<Price> price = getAPrice().get();

        when(priceRepositoryMock.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderedByPriorityDesc
                (
                    getPriceBOExample.getLdtApplicationDate(),
                    getPriceBOExample.getProductId(),
                    getPriceBOExample.getBrandId()
                )
            )
            .thenReturn(Optional.of(price));

        when(conversionServiceMock.convert
                (
                    price.get(0),
                    PriceBO.class
                )
            )
            .thenReturn(
                PriceBO.builder()
                .productId(price.get(0).getProduct().getId())
                .build()
            );

        PriceBO priceBO = sut.getPrice(getPriceBOExample);

        verify(priceRepositoryMock, atLeast(1))
            .findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderedByPriorityDesc(
                getPriceBOExample.getLdtApplicationDate(),
                getPriceBOExample.getProductId(),
                getPriceBOExample.getBrandId()
            );

        verify(conversionServiceMock, times(1))
            .convert(argCaptor.capture(), any(Class.class));

        assertEquals(price.get(0), argCaptor.getValue());
        assertEquals("98938983", priceBO.getProductId());
    }

    @Test
    void givenValidGetPriceBOWhenNotFoundRaiseExeption(){
        GetPriceBO getPriceBOExample = GetPriceBO.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .ldtApplicationDate(getValidLocalAppDate())
                .build();

        when(priceRepositoryMock.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderedByPriorityDesc
            (
                getPriceBOExample.getLdtApplicationDate(),
                getPriceBOExample.getProductId(),
                getPriceBOExample.getBrandId())
            )
            .thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> {
            sut.getPrice(getPriceBOExample);
        });

    }


    private Optional<List<Price>> getAPrice() {
        return Optional.of(List.of(Price.builder()
                .product(Product.builder().id(PRODUCT_ID).build())
                .id(PRICE_ID).build()));
    }

    private LocalDateTime getValidLocalAppDate() {
        return localDateTimeConversionUtils.convertStringToLocalDateTime(A_STRING_DATE);
    }
}
