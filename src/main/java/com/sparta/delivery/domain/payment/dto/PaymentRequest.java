package com.sparta.delivery.domain.payment.dto;

import com.sparta.delivery.domain.payment.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentRequest {

    @NotNull(message = "결제 방식은 필수입니다. ")
    private PaymentType paymentType;
}
