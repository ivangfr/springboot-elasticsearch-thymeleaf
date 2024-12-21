package com.ivanfranchin.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record SearchRequest(@Schema(title = "text to be searched", example = "DDR4") @NotBlank String text) {
}
