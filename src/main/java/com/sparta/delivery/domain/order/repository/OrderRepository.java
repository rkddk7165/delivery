package com.sparta.delivery.domain.order.repository;

import com.sparta.delivery.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomerId(Long id);

    List<Order> findAllByMenuOwnerId(Long id);
}
