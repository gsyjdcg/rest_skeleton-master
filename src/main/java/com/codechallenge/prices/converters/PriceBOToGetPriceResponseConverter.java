package com.codechallenge.prices.converters;

import com.codechallenge.common.controller.bo.CallDataResponse;
import com.codechallenge.prices.bo.external.GetPriceResponse;
import com.codechallenge.prices.bo.internal.PriceBO;
import com.codechallenge.common.util.LocalDateTimeConversionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PriceBOToGetPriceResponseConverter implements Converter<PriceBO, GetPriceResponse> {

    private LocalDateTimeConversionUtils localDateTimeConversionUtils;

    public PriceBOToGetPriceResponseConverter(LocalDateTimeConversionUtils localDateTimeConversionUtils) {
        this.localDateTimeConversionUtils = localDateTimeConversionUtils;
    }

    @Override
    public GetPriceResponse convert(PriceBO priceBO) {
        return GetPriceResponse.builder()
                .brandId(priceBO.getBrandId())
                .isoCurrency(priceBO.getIsoCurrency())
                .finalPrice(String.valueOf(priceBO.getFinalPrice()))
                .priceList(priceBO.getPriceList())
                .productId(priceBO.getProductId())
                .endDate(localDateTimeConversionUtils.convertLocalDateTimeToString(priceBO.getEndDate()))
                .startDate(localDateTimeConversionUtils.convertLocalDateTimeToString(priceBO.getStartDate()))
                .callData(CallDataResponse.builder()
                        .message("ok")
                        .build())
                .build();
    }
}
