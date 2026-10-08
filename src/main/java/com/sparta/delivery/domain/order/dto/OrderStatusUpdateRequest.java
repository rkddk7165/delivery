package com.sparta.delivery.domain.order.dto;

import com.sparta.delivery.domain.order.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderStatusUpdateRequest {

    @NotNull(message = "변경할 주문 상태는 필수입니다.")
    private OrderStatus status;
}