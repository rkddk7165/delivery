package com.sparta.delivery.domain.order.controller;

import com.sparta.delivery.global.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    //주문 생성
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody OrderRequest request
    ){


    }

    //주문 목록 조회
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @AuthenticationPrincipal AuthUser authUser
    ){

    }

    //주문 취소
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<> cancelOrder(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long orderId
    ){

    }

    //주문 상태 변경 (주문 수락, 배달 완료)
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequest request
    ){

    }

}
