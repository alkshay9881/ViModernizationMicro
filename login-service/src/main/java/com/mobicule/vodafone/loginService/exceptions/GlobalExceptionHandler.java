package com.mobicule.vodafone.loginService.exceptions;

import com.mobicule.vodafone.loginService.common.entities.Response;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MQException.class)
    public Response handleMQException(MQException ex) {
        return new Response(Response.ResponseStatus.FAILURE, ex.getMessage(), null);
    }
    @ExceptionHandler(Exception.class)
    public Response handleGeneric(Exception ex) {
        return new Response(Response.ResponseStatus.FAILURE, "Something went wrong", null);
    }
}
