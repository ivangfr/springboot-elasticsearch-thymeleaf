package com.ivanfranchin.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Set;

public record UpdateProductRequest(
        @Schema(title = "product name", example = "Apple 15\" MacBook Pro") String name,
        @Schema(title = "product description", example = "Apple 15\" MacBook Pro, Retina Display, 2.3GHz Intel Core i5 Dual Core, 8GB RAM, 128GB SSD, Space Gray, MPXQ2LL/A ") String description,
        @Schema(title = "product price", example = "1599.90") BigDecimal price,
        @Schema(title = "product categories", example = "[\"laptops\", \"apple\"]") Set<String> categories) {
}
