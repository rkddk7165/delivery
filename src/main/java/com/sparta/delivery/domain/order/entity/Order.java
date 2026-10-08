package com.sparta.delivery.domain.order.entity;

import com.sparta.delivery.domain.menu.entity.Menu;
import com.sparta.delivery.domain.order.enums.OrderStatus;
import com.sparta.delivery.domain.order.exception.InvalidOrderStatusException;
import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    public Order(Integer quantity, String address, Menu menu, User customer) {
        this.quantity = quantity;
        this.totalPrice = quantity * menu.getPrice();
        this.address = address;
        this.menu = menu;
        this.customer = customer;
        this.status = OrderStatus.ORDERED;
    }

    /**
     * 주문 상태 변경 메서드
     */
    public void cancel() {
        if (this.status != OrderStatus.ORDERED) {
            throw new InvalidOrderStatusException("주문 요청 상태에서만 취소할 수 있습니다.");
        }

        this.status = OrderStatus.CANCELED;
    }

    public void pay() {
        if (this.status != OrderStatus.ORDERED) {
            throw new InvalidOrderStatusException("주문 요청 상태에서만 결제할 수 있습니다.");
        }

        this.status = OrderStatus.PAID;
    }

    public void accept() {
        if (this.status != OrderStatus.PAID) {
            throw new InvalidOrderStatusException("결제 완료 상태에서만 주문을 수락할 수 있습니다.");
        }

        this.status = OrderStatus.ACCEPTED;
    }

    public void complete() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new InvalidOrderStatusException("주문 수락 상태에서만 완료할 수 있습니다.");
        }

        this.status = OrderStatus.COMPLETED;
    }
}
