package com.sparta.delivery.domain.order.exception;

public class OrderStatusConflictException extends RuntimeException {
    public OrderStatusConflictException() {
        super(
                "변경할 수 없는 주문 상태입니다."
        );
    }
}
