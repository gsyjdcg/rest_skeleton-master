package com.codechallenge.prices.bo.internal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductBO {
    private String id;
    private String name;
}
