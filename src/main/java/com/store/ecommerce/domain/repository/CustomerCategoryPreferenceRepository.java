package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.CustomerCategoryPreference;

import java.util.List;
import java.util.Optional;


public interface CustomerCategoryPreferenceRepository {

    CustomerCategoryPreference save(CustomerCategoryPreference preference);

    Optional<CustomerCategoryPreference> findById(Long id);

    List<CustomerCategoryPreference> findByCustomerId(Long customerId);

    Optional<CustomerCategoryPreference> findByCustomerIdAndCategoryId(Long customerId, Long categoryId);

    void deleteById(Long id);

    void deleteByCustomerIdAndCategoryId(Long customerId, Long categoryId);

    boolean existsByCustomerIdAndCategoryId(Long customerId, Long categoryId);
}
