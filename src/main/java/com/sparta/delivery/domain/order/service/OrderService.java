package com.sparta.delivery.domain.order.service;

import com.sparta.delivery.domain.menu.entity.Menu;
import com.sparta.delivery.domain.menu.enums.MenuStatus;
import com.sparta.delivery.domain.menu.exception.MenuNotFoundException;
import com.sparta.delivery.domain.menu.repository.MenuRepository;
import com.sparta.delivery.domain.order.dto.OrderRequest;
import com.sparta.delivery.domain.order.dto.OrderResponse;
import com.sparta.delivery.domain.order.dto.OrderStatusUpdateRequest;
import com.sparta.delivery.domain.order.entity.Order;
import com.sparta.delivery.domain.order.enums.OrderStatus;
import com.sparta.delivery.domain.order.exception.OrderNotFoundException;
import com.sparta.delivery.domain.order.exception.OrderStatusConflictException;
import com.sparta.delivery.domain.order.repository.OrderRepository;
import com.sparta.delivery.domain.user.entity.User;
import com.sparta.delivery.domain.user.enums.UserRole;
import com.sparta.delivery.domain.user.exception.UserNotFoundException;
import com.sparta.delivery.domain.user.repository.UserRepository;
import com.sparta.delivery.global.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    //주문 생성
    @Transactional
    public OrderResponse create(String username, OrderRequest request) {

        //고객 조회
        User customer = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new UserNotFoundException());

        //메뉴 조회
        Menu menu = menuRepository.findByIdAndStatus(request.getMenuId(), MenuStatus.ACTIVE)
                .orElseThrow(()->
                        new MenuNotFoundException());

        //주문 생성
        Order order = new Order(
                request.getQuantity(),
                request.getAddress(),
                menu,
                customer
        );

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }


    // 주문 목록 조회
    public List<OrderResponse> getOrders(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException()
                );

        List<Order> orders;

        if (user.getRole() == UserRole.CUSTOMER) {
            orders = orderRepository
                    .findAllByCustomerId(user.getId());

        } else if (user.getRole() == UserRole.OWNER) {
            orders = orderRepository
                    .findAllByMenuOwnerId(user.getId());

        } else {
            throw new ForbiddenException(
                    "주문 목록을 조회할 권한이 없습니다."
            );
        }

        return orders.stream()
                .map(OrderResponse::from)
                .toList();


    }

    // 주문 취소
    @Transactional
    public OrderResponse cancel(
            Long orderId,
            String username
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException()
                );

        if (!order.getCustomer()
                .getUsername()
                .equals(username)) {

            throw new ForbiddenException(
                    "본인의 주문만 취소할 수 있습니다."
            );
        }

        order.cancel();

        return OrderResponse.from(order);
    }

    // 주문 상태 변경
    @Transactional
    public OrderResponse updateStatus(
            Long orderId,
            String username,
            OrderStatusUpdateRequest request
    ) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException()
                );

        if (!order.getMenu()
                .getOwner()
                .getUsername()
                .equals(username)) {

            throw new ForbiddenException(
                    "본인 메뉴의 주문만 상태를 변경할 수 있습니다."
            );
        }

        OrderStatus newStatus = request.getStatus();

        if (newStatus == OrderStatus.ACCEPTED) {
            order.accept();

        } else if (newStatus == OrderStatus.COMPLETED) {
            order.complete();

        } else {
            throw new OrderStatusConflictException();
        }

        return OrderResponse.from(order);
    }
}
