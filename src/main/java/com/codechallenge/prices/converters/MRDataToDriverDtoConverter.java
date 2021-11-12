package com.codechallenge.prices.converters;

import com.codechallenge.f1.dto.Driver;
import com.codechallenge.f1.dto.DriverDto;
import com.codechallenge.f1.dto.MRData;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class MRDataToDriverDtoConverter implements Converter<MRData, DriverDto> {

    @Override
    public DriverDto convert(MRData mrData) {

        Driver driver = mrData.getDriverTable().getDrivers().get(0);

        return DriverDto.builder()
            .dateOfBirth(driver.getDateOfBirth())
            .familyName(driver.getFamilyName())
            .givenName(driver.getGivenName())
            .nationality(driver.getNationality())
            .url(driver.getUrl())
            .build();
    }
}
