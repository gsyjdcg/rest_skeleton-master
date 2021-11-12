package com.codechallenge.common.controller;

import com.codechallenge.common.controller.bo.BaseResponse;
import com.codechallenge.common.controller.bo.CallDataResponse;
import com.codechallenge.prices.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public BaseResponse handlePriceNotFound(PriceNotFoundException exception){

        return new BaseResponse(CallDataResponse.builder()
                .message("Price not found")
                .build());

    }
    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleDateTimeParseException(DateTimeParseException exception){

        return new BaseResponse(CallDataResponse.builder()
                .message("Bad time parse exception")
                .build());

    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleMissingParameterException(MissingServletRequestParameterException exception){

        return new BaseResponse(CallDataResponse.builder()
                .message(exception.getParameterName() + " not present")
                .build());

    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public BaseResponse handleArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception){

        return new BaseResponse(CallDataResponse.builder()
                .message(exception.getName() + " is type " + exception.getParameter().getParameterType().toString())
                .build());

    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleRestException(Exception exception){

        return new BaseResponse(CallDataResponse.builder()
                .message("Internal server error")
                .build());

    }

}
