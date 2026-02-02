package com.store.ecommerce.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


@Schema(description = "Add or update preference request (score optional)")
public record CustomerPreferenceRequest(
        @Schema(description = "Preference score 1-10 (optional)", example = "8", minimum = "1", maximum = "10")
        @Min(value = 1, message = "Score must be between 1 and 10")
        @Max(value = 10, message = "Score must be between 1 and 10")
        Integer score
) {}
