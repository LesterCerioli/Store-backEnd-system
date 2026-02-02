package com.store.ecommerce.domain.repository;

import com.store.ecommerce.domain.entity.Category;
import java.util.List;
import java.util.Optional;


public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    List<Category> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByName(String name);
}
