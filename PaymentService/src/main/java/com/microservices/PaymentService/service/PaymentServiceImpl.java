package com.microservices.PaymentService.service;

import com.microservices.PaymentService.entity.TransactionDetails;
import com.microservices.PaymentService.model.PaymentDetails;
import com.microservices.PaymentService.model.PaymentRequest;
import com.microservices.PaymentService.respository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentDetails getPaymentDetailsByOrderId(Long orderId) {
        Optional<TransactionDetails> transactionDetails = Optional.ofNullable(transactionDetailsRepository.findByOrderId(orderId));

        PaymentDetails paymentDetails = PaymentDetails.builder()
                .paymentId(transactionDetails.get().getId())
                .amount(transactionDetails.get().getAmount())
                .paymentDate(transactionDetails.get().getPaymentDate())
                .paymentMode(transactionDetails.get().getPaymentMode())
                .paymentStatus(transactionDetails.get().getPaymentStatus())
                .build();

        return paymentDetails;
    }
}
