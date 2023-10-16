package com.microservices.OrderService.external.client;

import com.microservices.OrderService.external.client.exception.CustomException;
import com.microservices.OrderService.external.client.model.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE/product")
public interface ProductService {
    @PutMapping("/reduceQuantity/{productId}")
    ResponseEntity<Void> reduceQuantity(@PathVariable Long productId, @RequestParam Long quantity) throws CustomException;

    @GetMapping("/{id}")
    ResponseEntity<OrderResponse.ProductDetails> getProductById(@PathVariable("id") long productId);
}
