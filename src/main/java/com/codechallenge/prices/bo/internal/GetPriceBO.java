package com.codechallenge.prices.bo.internal;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class GetPriceBO {
    private LocalDateTime ldtApplicationDate;
    private String productId;
    private Long brandId;
}
