package com.store.ecommerce.application.impl;

import com.store.ecommerce.application.service.AddressService;
import com.store.ecommerce.domain.entity.Address;
import com.store.ecommerce.domain.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address create(Address address) {
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address update(Long id, Address address) {
        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));
        existing.setStreet(address.getStreet());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setZipCode(address.getZipCode());
        existing.setCountry(address.getCountry());
        existing.setAddressType(address.getAddressType());
        return addressRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new IllegalArgumentException("Address not found: " + id);
        }
        addressRepository.deleteById(id);
    }
}
