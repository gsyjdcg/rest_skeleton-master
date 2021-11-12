package com.codechallenge.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LocalDateTimeConversionUtils {

    private DateTimeFormatter formatter;

    public LocalDateTimeConversionUtils(@Value("${codechallenge.pattern.stringtolocaldatetime}")
                                   String patternStringToLocalDateTime) {
        this.formatter = DateTimeFormatter.ofPattern(patternStringToLocalDateTime);
    }

    public LocalDateTime convertStringToLocalDateTime(String string) {
        return LocalDateTime.parse(string, formatter);
    }

    public String convertLocalDateTimeToString(LocalDateTime localDateTime){
        return localDateTime.format(formatter);
    }
}
