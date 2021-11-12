package com.codechallenge.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverTable {
  private String driverId;
  @JsonProperty("Drivers")
  private List<Driver> drivers;
}
