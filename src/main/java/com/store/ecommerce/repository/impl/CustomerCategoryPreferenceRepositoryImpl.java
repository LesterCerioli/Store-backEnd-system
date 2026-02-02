package com.store.ecommerce.repository.impl;

import com.store.ecommerce.domain.entity.CustomerCategoryPreference;
import com.store.ecommerce.domain.repository.CustomerCategoryPreferenceRepository;
import com.store.ecommerce.repository.jpa.JpaCustomerCategoryPreferenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CustomerCategoryPreferenceRepositoryImpl implements CustomerCategoryPreferenceRepository {

    private final JpaCustomerCategoryPreferenceRepository jpaRepository;

    public CustomerCategoryPreferenceRepositoryImpl(JpaCustomerCategoryPreferenceRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public CustomerCategoryPreference save(CustomerCategoryPreference preference) {
        return jpaRepository.save(preference);
    }

    @Override
    public Optional<CustomerCategoryPreference> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<CustomerCategoryPreference> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<CustomerCategoryPreference> findByCustomerIdAndCategoryId(Long customerId, Long categoryId) {
        return jpaRepository.findByCustomerIdAndCategoryId(customerId, categoryId);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByCustomerIdAndCategoryId(Long customerId, Long categoryId) {
        jpaRepository.deleteByCustomerIdAndCategoryId(customerId, categoryId);
    }

    @Override
    public boolean existsByCustomerIdAndCategoryId(Long customerId, Long categoryId) {
        return jpaRepository.existsByCustomerIdAndCategoryId(customerId, categoryId);
    }
}
