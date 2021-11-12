package com.codechallenge.prices;

import com.codechallenge.adapter.persistence.entity.Product;
import com.codechallenge.adapter.persistence.repository.ProductRepository;
import com.codechallenge.prices.bo.internal.ProductBO;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceDefaultImpl implements ProductService{

  private ProductRepository productRepository;
  private ConversionService conversionService;

  public ProductServiceDefaultImpl(
      ProductRepository productRepository,
      ConversionService conversionService) {
    this.productRepository = productRepository;
    this.conversionService = conversionService;
  }

  public void saveProduct(ProductBO productBO){
    productRepository.save(conversionService.convert(productBO, Product.class));
  }
}
