package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.CustomerProductPreference;

import java.util.List;
import java.util.Optional;


public interface CustomerProductPreferenceRepository {

    CustomerProductPreference save(CustomerProductPreference preference);

    Optional<CustomerProductPreference> findById(Long id);

    List<CustomerProductPreference> findByCustomerId(Long customerId);

    Optional<CustomerProductPreference> findByCustomerIdAndProductId(Long customerId, Long productId);

    void deleteById(Long id);

    void deleteByCustomerIdAndProductId(Long customerId, Long productId);

    boolean existsByCustomerIdAndProductId(Long customerId, Long productId);
}
