package com.codechallenge.f1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {
  private String url;
  private String givenName;
  private String familyName;
  private String dateOfBirth;
  private String nationality;
}
