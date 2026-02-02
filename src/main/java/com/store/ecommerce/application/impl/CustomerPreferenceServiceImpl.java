package com.store.ecommerce.application.impl;

import com.store.ecommerce.application.service.CustomerPreferenceService;
import com.store.ecommerce.domain.entity.Category;
import com.store.ecommerce.domain.entity.Customer;
import com.store.ecommerce.domain.entity.CustomerCategoryPreference;
import com.store.ecommerce.domain.entity.CustomerProductPreference;
import com.store.ecommerce.domain.entity.Product;
import com.store.ecommerce.domain.repository.CustomerCategoryPreferenceRepository;
import com.store.ecommerce.domain.repository.CustomerProductPreferenceRepository;
import com.store.ecommerce.domain.repository.CategoryRepository;
import com.store.ecommerce.domain.repository.CustomerRepository;
import com.store.ecommerce.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CustomerPreferenceServiceImpl implements CustomerPreferenceService {

    private static final int MAX_SCORE = 10;
    private static final int MIN_SCORE = 1;

    private final CustomerProductPreferenceRepository productPreferenceRepository;
    private final CustomerCategoryPreferenceRepository categoryPreferenceRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CustomerPreferenceServiceImpl(
            CustomerProductPreferenceRepository productPreferenceRepository,
            CustomerCategoryPreferenceRepository categoryPreferenceRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {
        this.productPreferenceRepository = productPreferenceRepository;
        this.categoryPreferenceRepository = categoryPreferenceRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CustomerProductPreference addProductPreference(Long customerId, Long productId, Integer score) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        Integer validScore = validateScore(score);

        return productPreferenceRepository.findByCustomerIdAndProductId(customerId, productId)
                .map(pref -> {
                    pref.setPreferenceScore(validScore);
                    return productPreferenceRepository.save(pref);
                })
                .orElseGet(() -> {
                    CustomerProductPreference pref = new CustomerProductPreference();
                    pref.setCustomer(customer);
                    pref.setProduct(product);
                    pref.setPreferenceScore(validScore);
                    return productPreferenceRepository.save(pref);
                });
    }

    @Override
    @Transactional
    public CustomerCategoryPreference addCategoryPreference(Long customerId, Long categoryId, Integer score) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
        Integer validScore = validateScore(score);

        return categoryPreferenceRepository.findByCustomerIdAndCategoryId(customerId, categoryId)
                .map(pref -> {
                    pref.setPreferenceScore(validScore);
                    return categoryPreferenceRepository.save(pref);
                })
                .orElseGet(() -> {
                    CustomerCategoryPreference pref = new CustomerCategoryPreference();
                    pref.setCustomer(customer);
                    pref.setCategory(category);
                    pref.setPreferenceScore(validScore);
                    return categoryPreferenceRepository.save(pref);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerProductPreference> getProductPreferences(Long customerId) {
        return productPreferenceRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerCategoryPreference> getCategoryPreferences(Long customerId) {
        return categoryPreferenceRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerPreferencesMap getCustomerPreferences(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }
        List<ProductPreferenceItem> products = productPreferenceRepository.findByCustomerId(customerId).stream()
                .map(p -> new ProductPreferenceItem(
                        p.getId(),
                        p.getProduct().getId(),
                        p.getProduct().getName(),
                        p.getPreferenceScore()))
                .toList();
        List<CategoryPreferenceItem> categories = categoryPreferenceRepository.findByCustomerId(customerId).stream()
                .map(c -> new CategoryPreferenceItem(
                        c.getId(),
                        c.getCategory().getId(),
                        c.getCategory().getName(),
                        c.getPreferenceScore()))
                .toList();
        return new CustomerPreferencesMap(customerId, products, categories);
    }

    @Override
    @Transactional
    public void removeProductPreference(Long customerId, Long productId) {
        if (!productPreferenceRepository.existsByCustomerIdAndProductId(customerId, productId)) {
            throw new IllegalArgumentException("Product preference not found for customer " + customerId + " and product " + productId);
        }
        productPreferenceRepository.deleteByCustomerIdAndProductId(customerId, productId);
    }

    @Override
    @Transactional
    public void removeCategoryPreference(Long customerId, Long categoryId) {
        if (!categoryPreferenceRepository.existsByCustomerIdAndCategoryId(customerId, categoryId)) {
            throw new IllegalArgumentException("Category preference not found for customer " + customerId + " and category " + categoryId);
        }
        categoryPreferenceRepository.deleteByCustomerIdAndCategoryId(customerId, categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerProductPreference> findProductPreferenceById(Long id) {
        return productPreferenceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerCategoryPreference> findCategoryPreferenceById(Long id) {
        return categoryPreferenceRepository.findById(id);
    }

    private Integer validateScore(Integer score) {
        if (score == null) return null;
        if (score < MIN_SCORE || score > MAX_SCORE) {
            throw new IllegalArgumentException("Preference score must be between " + MIN_SCORE + " and " + MAX_SCORE);
        }
        return score;
    }
}
