package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;


@Schema(description = "Category response")
public record CategoryResponse(
        @Schema(description = "Category ID") Long id,
        @Schema(description = "Category name") String name,
        @Schema(description = "Category description") String description,
        @Schema(description = "Creation timestamp") Instant createdAt,
        @Schema(description = "Last update timestamp") Instant updatedAt
) {}
