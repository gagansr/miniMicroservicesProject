package com.microservices.ProductService.service;

import com.microservices.ProductService.exceptionHandling.ProductServiceCustomException;
import com.microservices.ProductService.model.ProductRequest;
import com.microservices.ProductService.model.ProductResponse;

public interface ProductService {
    public long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId) throws ProductServiceCustomException;

    void reduceQuantity(Long productId, Long quantity) throws ProductServiceCustomException;
}
