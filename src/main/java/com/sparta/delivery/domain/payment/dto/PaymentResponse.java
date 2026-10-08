package com.sparta.delivery.domain.payment.dto;

import com.sparta.delivery.domain.payment.entity.Payment;
import com.sparta.delivery.domain.payment.enums.PaymentStatus;
import com.sparta.delivery.domain.payment.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponse {

    private Long id;
    private Long orderId;
    private Integer totalPrice;
    private PaymentType paymentType;
    private PaymentStatus status;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .orderId(payment.getOrder().getId())
                .totalPrice(payment.getTotalPrice())
                .paymentType(payment.getPaymentType())
                .status(payment.getStatus())
                .build();
    }
}