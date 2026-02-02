package com.store.ecommerce.api.mapper;

import com.store.ecommerce.api.dto.*;
import com.store.ecommerce.domain.entity.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class DtoMapper {

    public CategoryResponse toCategoryResponse(Category entity) {
        if (entity == null) return null;
        return new CategoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Category toCategory(CategoryRequest dto) {
        if (dto == null) return null;
        Category c = new Category();
        c.setName(dto.name());
        c.setDescription(dto.description());
        return c;
    }

    public void updateCategory(Category entity, CategoryRequest dto) {
        if (dto == null) return;
        entity.setName(dto.name());
        entity.setDescription(dto.description());
    }

    public CustomerResponse toCustomerResponse(Customer entity) {
        if (entity == null) return null;
        return new CustomerResponse(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Customer toCustomer(CustomerRequest dto) {
        if (dto == null) return null;
        Customer c = new Customer();
        c.setFirstName(dto.firstName());
        c.setLastName(dto.lastName());
        c.setEmail(dto.email());
        c.setPhone(dto.phone());
        return c;
    }

    public void updateCustomer(Customer entity, CustomerRequest dto) {
        if (dto == null) return;
        entity.setFirstName(dto.firstName());
        entity.setLastName(dto.lastName());
        entity.setEmail(dto.email());
        entity.setPhone(dto.phone());
    }

    public ProductResponse toProductResponse(Product entity) {
        if (entity == null) return null;
        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getSku(),
                entity.getCategory() != null ? entity.getCategory().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Product toProduct(ProductRequest dto) {
        if (dto == null) return null;
        Product p = new Product();
        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setPrice(dto.price());
        p.setStockQuantity(dto.stockQuantity());
        p.setSku(dto.sku());
        return p;
    }

    public void updateProduct(Product entity, ProductRequest dto) {
        if (dto == null) return;
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setStockQuantity(dto.stockQuantity());
        entity.setSku(dto.sku());
    }

    public OrderResponse toOrderResponse(Order entity) {
        if (entity == null) return null;
        var items = entity.getItems().stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());
        return new OrderResponse(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getStatus().name(),
                entity.getTotalAmount(),
                entity.getCustomer() != null ? entity.getCustomer().getId() : null,
                items,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public OrderItemResponse toOrderItemResponse(OrderItem entity) {
        if (entity == null) return null;
        return new OrderItemResponse(
                entity.getId(),
                entity.getProduct() != null ? entity.getProduct().getId() : null,
                entity.getProduct() != null ? entity.getProduct().getName() : null,
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getSubtotal()
        );
    }

    public AddressResponse toAddressResponse(Address entity) {
        if (entity == null) return null;
        return new AddressResponse(
                entity.getId(),
                entity.getStreet(),
                entity.getCity(),
                entity.getState(),
                entity.getZipCode(),
                entity.getCountry(),
                entity.getAddressType(),
                entity.getCustomer() != null ? entity.getCustomer().getId() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Address toAddress(AddressRequest dto) {
        if (dto == null) return null;
        Address a = new Address();
        a.setStreet(dto.street());
        a.setCity(dto.city());
        a.setState(dto.state());
        a.setZipCode(dto.zipCode());
        a.setCountry(dto.country());
        a.setAddressType(dto.addressType());
        return a;
    }

    public void updateAddress(Address entity, AddressRequest dto) {
        if (dto == null) return;
        entity.setStreet(dto.street());
        entity.setCity(dto.city());
        entity.setState(dto.state());
        entity.setZipCode(dto.zipCode());
        entity.setCountry(dto.country());
        entity.setAddressType(dto.addressType());
    }
}
