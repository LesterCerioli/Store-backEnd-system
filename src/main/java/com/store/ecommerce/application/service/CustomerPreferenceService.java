package com.store.ecommerce.application.service;

import com.store.ecommerce.domain.entity.CustomerCategoryPreference;
import com.store.ecommerce.domain.entity.CustomerProductPreference;

import java.util.List;
import java.util.Optional;


public interface CustomerPreferenceService {

    /**
     * Adds or updates a product preference for a customer.
     *
     * @param customerId customer identifier
     * @param productId  product identifier
     * @param score      preference score (1-10, optional)
     * @return the saved preference
     */
    CustomerProductPreference addProductPreference(Long customerId, Long productId, Integer score);

    /**
     * Adds or updates a category preference for a customer.
     *
     * @param customerId customer identifier
     * @param categoryId category identifier
     * @param score      preference score (1-10, optional)
     * @return the saved preference
     */
    CustomerCategoryPreference addCategoryPreference(Long customerId, Long categoryId, Integer score);

    /**
     * Gets all product preferences for a customer.
     */
    List<CustomerProductPreference> getProductPreferences(Long customerId);

    /**
     * Gets all category preferences for a customer.
     */
    List<CustomerCategoryPreference> getCategoryPreferences(Long customerId);

    /**
     * Gets full customer preferences mapping (products and categories).
     */
    CustomerPreferencesMap getCustomerPreferences(Long customerId);

    /**
     * Removes a product preference for a customer.
     */
    void removeProductPreference(Long customerId, Long productId);

    /**
     * Removes a category preference for a customer.
     */
    void removeCategoryPreference(Long customerId, Long categoryId);

    /**
     * Finds a product preference by id.
     */
    Optional<CustomerProductPreference> findProductPreferenceById(Long id);

    /**
     * Finds a category preference by id.
     */
    Optional<CustomerCategoryPreference> findCategoryPreferenceById(Long id);

    /**
     * DTO representing the full customer preferences map.
     */
    record CustomerPreferencesMap(
            Long customerId,
            List<ProductPreferenceItem> favoriteProducts,
            List<CategoryPreferenceItem> favoriteCategories
    ) {}

    record ProductPreferenceItem(Long id, Long productId, String productName, Integer score) {}
    record CategoryPreferenceItem(Long id, Long categoryId, String categoryName, Integer score) {}
}
