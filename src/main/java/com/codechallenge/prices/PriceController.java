package com.codechallenge.prices;

import com.codechallenge.common.util.LocalDateTimeConversionUtils;
import com.codechallenge.prices.bo.external.GetPriceResponse;
import com.codechallenge.prices.bo.internal.GetPriceBO;
import com.codechallenge.prices.bo.internal.PriceBO;
import com.codechallenge.prices.exception.PriceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
public class PriceController {

    PriceService priceService;
    ConversionService conversionService;
    LocalDateTimeConversionUtils localDateTimeConversionUtils;

    public PriceController(PriceService priceService,
        ConversionService conversionService,
        LocalDateTimeConversionUtils localDateTimeConversionUtils) {
        this.priceService = priceService;
        this.conversionService = conversionService;
        this.localDateTimeConversionUtils = localDateTimeConversionUtils;
    }

    @GetMapping
    public ResponseEntity<GetPriceResponse> getPrice(@RequestParam(name = "applicationDate") String applicationDate,
                                                     @RequestParam(name = "productId") String productId,
                                                     @RequestParam(name = "brandId") Long brandId
    ) throws PriceNotFoundException {
        PriceBO price = priceService.getPrice(GetPriceBO.builder()
                .brandId(brandId)
                .productId(productId)
                .ldtApplicationDate(localDateTimeConversionUtils.convertStringToLocalDateTime(applicationDate))
                .build());
        return ResponseEntity.ok().body(
                conversionService.convert(price, GetPriceResponse.class)
        );
    }

    @GetMapping(path="/all")

    public ResponseEntity<List<GetPriceResponse>> getAllPrices(@RequestParam(name = "productId") String productId)
        throws PriceNotFoundException {
        List<PriceBO> prices = priceService.getAllPrices(GetPriceBO.builder()
            .productId(productId)
            .build());

        List<GetPriceResponse> out = new ArrayList<>();

        for(PriceBO price:prices){
            out.add(conversionService.convert(price, GetPriceResponse.class));
        }

        return ResponseEntity.ok().body(out);
    }
}
