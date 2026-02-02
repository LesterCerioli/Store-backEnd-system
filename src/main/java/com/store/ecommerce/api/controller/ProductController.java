package com.store.ecommerce.api.controller;

import com.store.ecommerce.api.dto.ProductRequest;
import com.store.ecommerce.api.dto.ProductResponse;
import com.store.ecommerce.api.mapper.DtoMapper;
import com.store.ecommerce.application.service.CategoryService;
import com.store.ecommerce.application.service.ProductService;
import com.store.ecommerce.domain.entity.Category;
import com.store.ecommerce.domain.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Products", description = "Product catalog management")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final DtoMapper mapper;

    public ProductController(ProductService productService, CategoryService categoryService, DtoMapper mapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Create a product")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product entity = mapper.toProduct(request);
        if (request.categoryId() != null) {
            Category category = categoryService.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.categoryId()));
            entity.setCategory(category);
        }
        Product saved = productService.create(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toProductResponse(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        Product entity = mapper.toProduct(request);
        if (request.categoryId() != null) {
            Category category = categoryService.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found: " + request.categoryId()));
            entity.setCategory(category);
        }
        Product updated = productService.update(id, entity);
        return ResponseEntity.ok(mapper.toProductResponse(updated));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return productService.findById(id)
                .map(mapper::toProductResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "List all products")
    public List<ProductResponse> list(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return productService.findByCategoryId(categoryId).stream()
                    .map(mapper::toProductResponse)
                    .toList();
        }
        return productService.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
