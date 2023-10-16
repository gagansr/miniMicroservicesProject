package com.microservices.OrderService.service;

import com.microservices.OrderService.external.client.exception.CustomException;
import com.microservices.OrderService.external.client.model.OrderResponse;
import com.microservices.OrderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest) throws CustomException;

    OrderResponse getOrderById(Long orderId);
}
