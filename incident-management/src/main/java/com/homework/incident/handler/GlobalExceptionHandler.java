package com.homework.incident.handler;

import com.homework.incident.common.ResponseCode;
import com.homework.incident.common.ResponseWrapper;
import org.springframework.web.bind.annotation.ExceptionHandler;


public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseWrapper<String> handleException(Exception ex) {
        return new ResponseWrapper.Builder<String>().code(ResponseCode.FAILURE.getCode()).message(ex.getMessage()).build();
    }
}
