package com.codechallenge.f1;

import com.codechallenge.f1.dto.DriverDto;
import com.codechallenge.f1.dto.MRData;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class F1Service {

  private F1FeignClient f1FeignClient;
  private ConversionService conversionService;

  public F1Service(F1FeignClient f1FeignClient,
      ConversionService conversionService) {
    this.f1FeignClient = f1FeignClient;
    this.conversionService = conversionService;
  }

  public DriverDto getDriverInfo(String code){
    MRData result = f1FeignClient.driver(code).getMrData();
    return conversionService.convert(result, DriverDto.class);
  }
}
