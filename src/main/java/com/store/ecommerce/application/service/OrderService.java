package com.store.ecommerce.application.service;

import com.store.ecommerce.domain.entity.Order;
import java.util.List;
import java.util.Optional;


public interface OrderService {

    Order create(Order order);

    Order updateStatus(Long id, Order.OrderStatus status);

    Optional<Order> findById(Long id);

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAll();

    List<Order> findByCustomerId(Long customerId);

    void deleteById(Long id);
}
