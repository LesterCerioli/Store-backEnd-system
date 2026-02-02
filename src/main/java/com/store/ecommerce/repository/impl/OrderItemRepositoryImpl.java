package com.store.ecommerce.repository.impl;

import com.store.ecommerce.domain.entity.OrderItem;
import com.store.ecommerce.domain.repository.OrderItemRepository;
import com.store.ecommerce.repository.jpa.JpaOrderItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final JpaOrderItemRepository jpaRepository;

    public OrderItemRepositoryImpl(JpaOrderItemRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        return jpaRepository.save(orderItem);
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return jpaRepository.findByOrderId(orderId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
