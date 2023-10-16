package com.microservices.ProductService.exceptionHandling;

import lombok.Data;

@Data
public class ProductServiceCustomException extends Exception{
    String errorCode;

    public ProductServiceCustomException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}
