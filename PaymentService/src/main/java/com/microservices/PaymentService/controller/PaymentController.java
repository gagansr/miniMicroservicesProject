package com.microservices.PaymentService.controller;

import com.microservices.PaymentService.model.PaymentDetails;
import com.microservices.PaymentService.model.PaymentRequest;
import com.microservices.PaymentService.model.PaymentResponse;
import com.microservices.PaymentService.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest),
                HttpStatus.OK
        );
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentDetails> getPaymentOrderId(@PathVariable Long orderId){
        return new ResponseEntity<>(paymentService.getPaymentDetailsByOrderId(orderId),HttpStatus.OK);
    }
}
