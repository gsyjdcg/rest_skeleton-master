package com.codechallenge.prices.converters;

import com.codechallenge.adapter.persistence.entity.Product;
import com.codechallenge.prices.bo.internal.ProductBO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductBOToProductConverter implements Converter<ProductBO, Product> {

    @Override
    public Product convert(ProductBO origin) {
        return Product.builder()
            .id(origin.getId())
            .name(origin.getName())
            .build();
    }
}
