package com.microservices.OrderService.external.client.exception;

import lombok.Data;

@Data
public class CustomException extends RuntimeException{
    private String ErrorCode;
    private int status;

    public CustomException(String message, String errorCode, int status){
        super(message);
        this.ErrorCode = errorCode;
        this.status = status;
    }
}
