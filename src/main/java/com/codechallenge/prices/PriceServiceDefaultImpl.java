package com.codechallenge.prices;

import com.codechallenge.adapter.persistence.entity.Price;
import com.codechallenge.adapter.persistence.entity.Product;
import com.codechallenge.adapter.persistence.repository.PriceRepository;
import com.codechallenge.prices.bo.internal.GetPriceBO;
import com.codechallenge.prices.bo.internal.PriceBO;
import com.codechallenge.prices.exception.PriceNotFoundException;
import java.util.ArrayList;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceServiceDefaultImpl implements PriceService {

    private PriceRepository priceRepository;
    private ConversionService conversionService;

    public PriceServiceDefaultImpl(PriceRepository priceRepository, ConversionService conversionService) {
        this.priceRepository = priceRepository;
        this.conversionService = conversionService;
    }

    public PriceBO getPrice(GetPriceBO getPriceBO) throws PriceNotFoundException {

        List<Price> prices = priceRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualAndProductIdAndBrandIdOrderedByPriorityDesc
                (
                        getPriceBO.getLdtApplicationDate(),
                        getPriceBO.getProductId(),
                        getPriceBO.getBrandId()
                )
                .orElseThrow(PriceNotFoundException::new);
        if (prices.isEmpty()) throw new PriceNotFoundException();

        //cojo el primero por que en caso de haber varios ya vienen ordenados descendentemente por prioridad
        return conversionService.convert(prices.get(0), PriceBO.class);
    }

    public List<PriceBO> getAllPrices(GetPriceBO getPriceBO) throws PriceNotFoundException {
        List<Price> prices = priceRepository.findByProductOrderByStartDateAsc(Product.builder()
            .id(getPriceBO.getProductId())
            .build()).orElseThrow(PriceNotFoundException::new);
        if (prices.isEmpty()) throw new PriceNotFoundException();

        List<PriceBO> out = new ArrayList<>();
        for(Price price:prices){
            out.add(conversionService.convert(price, PriceBO.class));
        }

        return out;
    }
}
