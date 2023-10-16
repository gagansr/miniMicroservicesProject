package com.microservices.PaymentService.respository;

import com.microservices.PaymentService.entity.TransactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TransactionDetails, Long> {
//    Optional<TransactionDetails> findByOrderId(Long paymentId);

    TransactionDetails findByOrderId(long orderId);
}
