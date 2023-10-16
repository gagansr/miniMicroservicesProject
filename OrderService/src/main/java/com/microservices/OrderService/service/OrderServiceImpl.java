package com.microservices.OrderService.service;

import com.microservices.OrderService.entity.Order;
import com.microservices.OrderService.external.client.PaymentService;
import com.microservices.OrderService.external.client.ProductService;
import com.microservices.OrderService.external.client.exception.CustomException;
import com.microservices.OrderService.external.client.model.OrderResponse;
import com.microservices.OrderService.external.client.model.PaymentRequest;
import com.microservices.OrderService.external.client.model.PaymentResponse;
import com.microservices.OrderService.repository.OrderRepository;
import com.microservices.OrderService.model.OrderRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Override
    public long placeOrder(OrderRequest orderRequest) throws CustomException {
        log.info("Saving Order to DB");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();

        log.info("Checking quantity of product.");

        productService.reduceQuantity(order.getProductId(),order.getQuantity());

        order =orderRepository.save(order);
        log.info("Saved th given order : {}",order);

        log.info("Calling Payment service : {}",order);

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .amount(order.getAmount())
                .paymentMode(orderRequest.getPaymentMode())
                .build();

        String orderStatus = null;

        try {
            paymentService.doPayment(paymentRequest);
            orderStatus = "Placed";
            log.info("Payment is done..! Changing order status to {}",orderStatus);
        }catch (Exception e){
            orderStatus = "Failed";
            log.info("Payment is Failed ..! Changing order status to {} and error is : {}",orderStatus,e.getMessage());
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        log.info("Order Places successfully with Order Id: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(
                        "Unable to find order with given id",
                        "ORDER_NOT_FOUND",
                        404)
                ));
       ResponseEntity<OrderResponse.ProductDetails> productDetails =  productService.getProductById(order.get().getProductId());

       ResponseEntity<OrderResponse.PaymentDetails> paymentDetails =  paymentService.getPaymentOrderId(order.get().getId());

       OrderResponse orderResponse = OrderResponse.builder()
                .orderId(order.get().getId())
                .orderStatus(order.get().getOrderStatus())
                .amount(order.get().getAmount())
                .orderStatus(order.get().getOrderStatus())
                .productDetails(productDetails.getBody())
               .paymentDetails(paymentDetails.getBody())
                .build();

        return orderResponse;
    }
}
