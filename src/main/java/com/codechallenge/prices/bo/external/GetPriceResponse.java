package com.codechallenge.prices.bo.external;

import com.codechallenge.common.controller.bo.BaseResponse;
import com.codechallenge.common.controller.bo.CallDataResponse;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@EqualsAndHashCode(callSuper = true)
public class GetPriceResponse extends BaseResponse {

    private String productId;
    private Long brandId;
    private Integer priceList;
    private String startDate;
    private String endDate;
    private String finalPrice;
    private String isoCurrency;

    @Builder
    public GetPriceResponse(CallDataResponse callData, String productId, Long brandId,
        Integer priceList, String startDate, String endDate, String finalPrice, String isoCurrency) {
        super(callData);
        this.productId = productId;
        this.brandId = brandId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.finalPrice = finalPrice;
        this.isoCurrency = isoCurrency;
    }
}
