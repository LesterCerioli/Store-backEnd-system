package com.store.ecommerce.api.controller;

import com.store.ecommerce.api.dto.CategoryRequest;
import com.store.ecommerce.api.dto.CategoryResponse;
import com.store.ecommerce.api.mapper.DtoMapper;
import com.store.ecommerce.application.service.CategoryService;
import com.store.ecommerce.domain.entity.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/categories")
@Tag(name = "Categories", description = "Product category management")
public class CategoryController {

    private final CategoryService categoryService;
    private final DtoMapper mapper;

    public CategoryController(CategoryService categoryService, DtoMapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a category")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        Category entity = mapper.toCategory(request);
        Category saved = categoryService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toCategoryResponse(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {
        Category entity = mapper.toCategory(request);
        Category updated = categoryService.update(id, entity);
        return ResponseEntity.ok(mapper.toCategoryResponse(updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(mapper::toCategoryResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all categories")
    public List<CategoryResponse> list() {
        return categoryService.findAll().stream()
                .map(mapper::toCategoryResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
