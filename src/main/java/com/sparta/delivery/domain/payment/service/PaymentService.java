package com.sparta.delivery.domain.payment.service;

import com.sparta.delivery.domain.order.entity.Order;
import com.sparta.delivery.domain.order.exception.OrderNotFoundException;
import com.sparta.delivery.domain.order.repository.OrderRepository;
import com.sparta.delivery.domain.payment.dto.PaymentRequest;
import com.sparta.delivery.domain.payment.dto.PaymentResponse;
import com.sparta.delivery.domain.payment.entity.Payment;
import com.sparta.delivery.domain.payment.enums.PaymentStatus;
import com.sparta.delivery.domain.payment.enums.PaymentType;
import com.sparta.delivery.domain.payment.exception.InvalidPaymentException;
import com.sparta.delivery.domain.payment.repository.PaymentRepository;
import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.domain.user.exception.UserNotFoundException;
import com.sparta.delivery.domain.user.repository.UserRepository;
import com.sparta.delivery.global.exception.ForbiddenException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    //결제 생성
    public PaymentResponse pay(String username, Long orderId, PaymentRequest request) {


        //주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->
                        new OrderNotFoundException());

        //본인주문이 아닐 경우
        if(!order.getCustomer().getUsername().equals(username)){
            throw new ForbiddenException("본인의 주문만 겾레할 수 있습니다.");
        }

        //결제방식이 카드가 아닐 경우
        if (request.getPaymentType() != PaymentType.CARD) {
            throw new InvalidPaymentException("카드 결제만 가능합니다.");
        }


        //주문 status 변경
        order.pay();

        //payment 생성
        Payment payment = new Payment(order, request.getPaymentType());

        Payment savedPayment = paymentRepository.save(payment);

        return PaymentResponse.from(payment);

    }
}
