package com.ivanfranchin.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class UpdateProductRequest {

    @Schema(title = "product name", example = "Apple 15\" MacBook Pro")
    private String name;

    @Schema(
            title = "product description",
            example = "Apple 15\" MacBook Pro, Retina Display, 2.3GHz Intel Core i5 Dual Core, 8GB RAM, 128GB SSD, Space Gray, MPXQ2LL/A ")
    private String description;

    @Schema(title = "product price", example = "1599.90")
    private BigDecimal price;

    @Schema(title = "product categories", example = "[\"laptops\", \"apple\"]")
    private Set<String> categories;
}
