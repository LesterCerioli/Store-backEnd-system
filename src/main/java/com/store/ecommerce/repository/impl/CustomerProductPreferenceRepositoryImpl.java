package com.store.ecommerce.repository.impl;

import com.store.ecommerce.domain.entity.CustomerProductPreference;
import com.store.ecommerce.domain.repository.CustomerProductPreferenceRepository;
import com.store.ecommerce.repository.jpa.JpaCustomerProductPreferenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CustomerProductPreferenceRepositoryImpl implements CustomerProductPreferenceRepository {

    private final JpaCustomerProductPreferenceRepository jpaRepository;

    public CustomerProductPreferenceRepositoryImpl(JpaCustomerProductPreferenceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CustomerProductPreference save(CustomerProductPreference preference) {
        return jpaRepository.save(preference);
    }

    @Override
    public Optional<CustomerProductPreference> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<CustomerProductPreference> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<CustomerProductPreference> findByCustomerIdAndProductId(Long customerId, Long productId) {
        return jpaRepository.findByCustomerIdAndProductId(customerId, productId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByCustomerIdAndProductId(Long customerId, Long productId) {
        jpaRepository.deleteByCustomerIdAndProductId(customerId, productId);
    }

    @Override
    public boolean existsByCustomerIdAndProductId(Long customerId, Long productId) {
        return jpaRepository.existsByCustomerIdAndProductId(customerId, productId);
    }
}
