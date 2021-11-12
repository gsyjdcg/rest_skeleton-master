package com.codechallenge.f1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MRData {
  private String xmlns;
  private String series;
  private String url;
  private String limit;
  private String offset;
  private String total;
  @JsonProperty("DriverTable")
  private DriverTable driverTable;
}
