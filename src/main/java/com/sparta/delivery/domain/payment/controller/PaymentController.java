package com.sparta.delivery.domain.payment.controller;

import com.sparta.delivery.domain.payment.dto.PaymentRequest;
import com.sparta.delivery.domain.payment.dto.PaymentResponse;
import com.sparta.delivery.domain.payment.service.PaymentService;
import com.sparta.delivery.global.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class PaymentController {

    private final PaymentService paymentService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{orderId}/payments")
    public ResponseEntity<PaymentResponse> pay(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long orderId,
            @Valid @RequestBody PaymentRequest request
            ){
        PaymentResponse response = paymentService.pay(authUser.username(),orderId, request);

        return ResponseEntity
                .status(201)
                .body(response);
    }
}
