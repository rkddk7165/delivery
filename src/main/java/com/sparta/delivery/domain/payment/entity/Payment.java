package com.sparta.delivery.domain.payment.entity;

import com.sparta.delivery.domain.order.entity.Order;
import com.sparta.delivery.domain.payment.enums.PaymentStatus;
import com.sparta.delivery.domain.payment.enums.PaymentType;
import com.sparta.delivery.domain.user.enums.UserRole;
import com.sparta.delivery.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Payment(PaymentType paymentType, Order order) {
        this.totalPrice = order.getTotalPrice();
        this.paymentType = paymentType;
        this.status = PaymentStatus.COMPLETED;
        this.order = order;
    }

    public void cancel(){
        this.status = PaymentStatus.CANCELED;
    }

}
