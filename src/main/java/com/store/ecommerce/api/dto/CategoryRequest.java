package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Schema(description = "Category create/update request")
public record CategoryRequest(
        @Schema(description = "Category name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Electronics")
        @NotBlank(message = "Name is required")
        @Size(max = 100)
        String name,

        @Schema(description = "Category description", example = "Electronic devices and accessories")
        @Size(max = 500)
        String description
) {}
