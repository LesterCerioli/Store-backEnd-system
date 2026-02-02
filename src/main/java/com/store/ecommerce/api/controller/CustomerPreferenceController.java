package com.store.ecommerce.api.controller;

import com.store.ecommerce.api.dto.CustomerPreferenceRequest;
import com.store.ecommerce.api.dto.CustomerPreferenceResponse;
import com.store.ecommerce.application.service.CustomerPreferenceService;
import com.store.ecommerce.domain.entity.CustomerCategoryPreference;
import com.store.ecommerce.domain.entity.CustomerProductPreference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for customer preferences (products and categories).
 * Single Responsibility: HTTP handling for customer preferences only.
 */
@RestController
@RequestMapping("/v1/customers/{customerId}/preferences")
@Tag(name = "Customer Preferences", description = "Customer product and category preferences")
public class CustomerPreferenceController {

    private final CustomerPreferenceService customerPreferenceService;

    public CustomerPreferenceController(CustomerPreferenceService customerPreferenceService) {
        this.customerPreferenceService = customerPreferenceService;
    }

    @PostMapping("/products/{productId}")
    @Operation(summary = "Add or update product preference")
    public ResponseEntity<ProductPreferenceDto> addProductPreference(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @Valid @RequestBody(required = false) CustomerPreferenceRequest request) {
        Integer score = request != null ? request.score() : null;
        CustomerProductPreference pref = customerPreferenceService.addProductPreference(customerId, productId, score);
        return ResponseEntity.status(HttpStatus.CREATED).body(toProductDto(pref));
    }

    @PostMapping("/categories/{categoryId}")
    @Operation(summary = "Add or update category preference")
    public ResponseEntity<CategoryPreferenceDto> addCategoryPreference(
            @PathVariable Long customerId,
            @PathVariable Long categoryId,
            @Valid @RequestBody(required = false) CustomerPreferenceRequest request) {
        Integer score = request != null ? request.score() : null;
        CustomerCategoryPreference pref = customerPreferenceService.addCategoryPreference(customerId, categoryId, score);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCategoryDto(pref));
    }

    @GetMapping
    @Operation(summary = "Get full customer preferences map (products and categories)")
    public ResponseEntity<CustomerPreferenceResponse> getCustomerPreferences(@PathVariable Long customerId) {
        CustomerPreferenceService.CustomerPreferencesMap map = customerPreferenceService.getCustomerPreferences(customerId);
        return ResponseEntity.ok(CustomerPreferenceResponse.from(map));
    }

    @GetMapping("/products")
    @Operation(summary = "Get product preferences only")
    public List<ProductPreferenceDto> getProductPreferences(@PathVariable Long customerId) {
        return customerPreferenceService.getProductPreferences(customerId).stream()
                .map(this::toProductDto)
                .toList();
    }

    @GetMapping("/categories")
    @Operation(summary = "Get category preferences only")
    public List<CategoryPreferenceDto> getCategoryPreferences(@PathVariable Long customerId) {
        return customerPreferenceService.getCategoryPreferences(customerId).stream()
                .map(this::toCategoryDto)
                .toList();
    }

    @DeleteMapping("/products/{productId}")
    @Operation(summary = "Remove product preference")
    public ResponseEntity<Void> removeProductPreference(
            @PathVariable Long customerId,
            @PathVariable Long productId) {
        customerPreferenceService.removeProductPreference(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/categories/{categoryId}")
    @Operation(summary = "Remove category preference")
    public ResponseEntity<Void> removeCategoryPreference(
            @PathVariable Long customerId,
            @PathVariable Long categoryId) {
        customerPreferenceService.removeCategoryPreference(customerId, categoryId);
        return ResponseEntity.noContent().build();
    }

    private ProductPreferenceDto toProductDto(CustomerProductPreference p) {
        return new ProductPreferenceDto(
                p.getId(),
                p.getCustomer().getId(),
                p.getProduct().getId(),
                p.getProduct().getName(),
                p.getPreferenceScore()
        );
    }

    private CategoryPreferenceDto toCategoryDto(CustomerCategoryPreference c) {
        return new CategoryPreferenceDto(
                c.getId(),
                c.getCustomer().getId(),
                c.getCategory().getId(),
                c.getCategory().getName(),
                c.getPreferenceScore()
        );
    }

    public record ProductPreferenceDto(Long id, Long customerId, Long productId, String productName, Integer score) {}
    public record CategoryPreferenceDto(Long id, Long customerId, Long categoryId, String categoryName, Integer score) {}
}
