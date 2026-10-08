package com.sparta.delivery.domain.order.dto;

import com.sparta.delivery.domain.order.entity.Order;
import com.sparta.delivery.domain.order.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponse {

    private Long id;
    private Long menuId;
    private String menuName;
    private Integer quantity;
    private Integer totalPrice;
    private String address;
    private OrderStatus status;

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .menuId(order.getMenu().getId())
                .menuName(order.getMenu().getName())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .address(order.getAddress())
                .status(order.getStatus())
                .build();
    }
}