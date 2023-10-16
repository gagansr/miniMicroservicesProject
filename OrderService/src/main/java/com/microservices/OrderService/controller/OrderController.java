package com.microservices.OrderService.controller;

import com.microservices.OrderService.external.client.exception.CustomException;
import com.microservices.OrderService.external.client.model.OrderResponse;
import com.microservices.OrderService.model.OrderRequest;
import com.microservices.OrderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) throws Exception {

        log.info("placing order with given data {}",orderRequest);

        long orderID = orderService.placeOrder(orderRequest);

        log.info("Order has been placed : {}",orderID);

        return new ResponseEntity<>(orderID, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId){
        return new ResponseEntity<>(
                orderService.getOrderById(orderId),
                HttpStatus.OK
        );
    }
}
