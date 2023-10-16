package com.microservices.OrderService.external.client;

import com.microservices.OrderService.external.client.model.OrderResponse;
import com.microservices.OrderService.external.client.model.PaymentRequest;
import com.microservices.OrderService.external.client.model.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE/payment")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    @GetMapping("{/paymentId}")
    ResponseEntity<OrderResponse.PaymentDetails> getPaymentOrderId(@PathVariable Long paymentId);

}
