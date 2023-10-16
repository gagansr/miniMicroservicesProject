package com.microservices.OrderService.external.client.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.OrderService.external.client.exception.CustomException;
import com.microservices.OrderService.external.client.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ErrorResponse errorResponse = objectMapper.readValue(response.body().asInputStream(),ErrorResponse.class);

            return new CustomException(errorResponse.getMessage(), errorResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error","INTERNAL_SERVER_ERROR",response.status());
        }
    }
}
