package com.codechallenge.prices.bo.internal;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PriceBO {
    private String productId;
    private Long brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Float finalPrice;
    private String isoCurrency;
}
