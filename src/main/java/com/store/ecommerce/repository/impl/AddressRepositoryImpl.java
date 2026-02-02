package com.store.ecommerce.repository.impl;

import com.store.ecommerce.domain.entity.Address;
import com.store.ecommerce.domain.repository.AddressRepository;
import com.store.ecommerce.repository.jpa.JpaAddressRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class AddressRepositoryImpl implements AddressRepository {

    private final JpaAddressRepository jpaRepository;

    public AddressRepositoryImpl(JpaAddressRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Address save(Address address) {
        return jpaRepository.save(address);
    }

    @Override
    public Optional<Address> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Address> findByCustomerId(Long customerId) {
        return jpaRepository.findByCustomerId(customerId);
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
