package com.codechallenge.prices;

import com.codechallenge.common.controller.bo.BaseResponse;
import com.codechallenge.common.controller.bo.CallDataResponse;
import com.codechallenge.prices.bo.internal.ProductBO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<BaseResponse> saveProduct(@RequestBody ProductBO productBO){
    productService.saveProduct(productBO);
    return ResponseEntity.ok().body(new BaseResponse(CallDataResponse.builder()
        .message("ok")
        .build()));
  }

}
