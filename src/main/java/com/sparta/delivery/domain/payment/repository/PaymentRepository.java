package com.sparta.delivery.domain.payment.repository;

import com.sparta.delivery.domain.payment.entity.Payment;
import com.sparta.delivery.domain.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderIdAndStatus(Long orderId, PaymentStatus paymentStatus);
}
