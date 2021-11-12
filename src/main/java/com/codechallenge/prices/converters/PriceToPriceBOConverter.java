package com.codechallenge.prices.converters;

import com.codechallenge.adapter.persistence.entity.Price;
import com.codechallenge.prices.bo.internal.PriceBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PriceToPriceBOConverter implements Converter<Price, PriceBO> {

    @Override
    public PriceBO convert(Price price) {
        return PriceBO.builder()
                .brandId(price.getBrand().getId())
                .endDate(price.getEndDate())
                .finalPrice(price.getPrice())
                .isoCurrency(price.getIsoCurrency())
                .priceList(price.getPriceList())
                .productId(price.getProduct().getId())
                .startDate(price.getStartDate())
                .build();
    }
}
