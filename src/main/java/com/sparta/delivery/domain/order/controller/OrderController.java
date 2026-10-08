package com.sparta.delivery.domain.order.controller;

import com.sparta.delivery.domain.order.dto.OrderRequest;
import com.sparta.delivery.domain.order.dto.OrderResponse;
import com.sparta.delivery.domain.order.dto.OrderStatusUpdateRequest;
import com.sparta.delivery.domain.order.service.OrderService;
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

    private final OrderService orderService;

    //주문 생성
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody OrderRequest request
    ){
        OrderResponse response = orderService.create(authUser.username(), request);

        return ResponseEntity
                .status(201)
                .body(response);


    }

    //주문 목록 조회
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok(
                orderService.getOrders(authUser.username())
        );
    }

    //주문 취소
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long orderId
    ){
        OrderResponse response = orderService.cancel(orderId, authUser.username());

        return ResponseEntity
                .status(200)
                .body(response);
    }

    //주문 상태 변경 (주문 수락, 배달 완료)
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequest request
    ){
        OrderResponse response = orderService.updateStatus(orderId, authUser.username(), request);

        return ResponseEntity
                .status(200)
                .body(response);
    }

}
