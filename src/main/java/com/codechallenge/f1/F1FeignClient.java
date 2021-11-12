package com.codechallenge.f1;

import com.codechallenge.f1.dto.DriverFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "f1fc", url= "${f1.api.basepath}")
public interface F1FeignClient {
  @RequestMapping(method = RequestMethod.GET, value="${f1.api.drivers}/{driver}.json")
  DriverFeign driver(@PathVariable("driver") String driver);
}
