package com.store.ecommerce.api.dto;

import com.store.ecommerce.application.service.CustomerPreferenceService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;


@Schema(description = "Customer preferences response - products and categories")
public record CustomerPreferenceResponse(
        @Schema(description = "Customer ID") Long customerId,
        @Schema(description = "Favorite products") List<ProductPreferenceResponse> favoriteProducts,
        @Schema(description = "Preferred categories") List<CategoryPreferenceResponse> favoriteCategories
) {
    public static CustomerPreferenceResponse from(CustomerPreferenceService.CustomerPreferencesMap map) {
        List<ProductPreferenceResponse> products = map.favoriteProducts().stream()
                .map(p -> new ProductPreferenceResponse(p.id(), p.productId(), p.productName(), p.score()))
                .toList();
        List<CategoryPreferenceResponse> categories = map.favoriteCategories().stream()
                .map(c -> new CategoryPreferenceResponse(c.id(), c.categoryId(), c.categoryName(), c.score()))
                .toList();
        return new CustomerPreferenceResponse(map.customerId(), products, categories);
    }

    @Schema(description = "Product preference item")
    public record ProductPreferenceResponse(Long id, Long productId, String productName, Integer score) {}

    @Schema(description = "Category preference item")
    public record CategoryPreferenceResponse(Long id, Long categoryId, String categoryName, Integer score) {}
}
