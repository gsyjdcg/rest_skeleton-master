package com.codechallenge.prices;

import com.codechallenge.prices.bo.internal.GetPriceBO;
import com.codechallenge.prices.bo.internal.PriceBO;
import com.codechallenge.prices.exception.PriceNotFoundException;
import java.util.List;

public interface PriceService {
    PriceBO getPrice(GetPriceBO getPriceBO) throws PriceNotFoundException;
    List<PriceBO> getAllPrices(GetPriceBO getPriceBO) throws PriceNotFoundException;
}
