package com.codechallenge.f1;

import com.codechallenge.f1.dto.DriverDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/f1-driver")
public class F1DriverController {

  private F1Service f1Service;

  public F1DriverController(F1Service f1Service) {
    this.f1Service = f1Service;
  }

  @GetMapping
  public ResponseEntity<DriverDto> getDriver(@RequestParam(name = "driver") String driver){

    DriverDto returned = f1Service.getDriverInfo(driver);
    return ResponseEntity.ok().body(returned);
  }

}
