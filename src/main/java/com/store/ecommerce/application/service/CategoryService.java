package com.store.ecommerce.application.service;

import com.store.ecommerce.domain.entity.Category;
import java.util.List;
import java.util.Optional;


public interface CategoryService {

    Category create(Category category);

    Category update(Long id, Category category);

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    void deleteById(Long id);
}
