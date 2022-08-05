package com.ivanfranchin.productapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
public class CreateProductRequest {

    @Schema(title = "product name", example = "Apple 13\" MacBook Pro")
    @NotBlank
    private String name;

    @Schema(
            title = "product description",
            example = "Apple 13\" MacBook Pro, Retina Display, 2.3GHz Intel Core i5 Dual Core, 8GB RAM, 128GB SSD, Space Gray, MPXQ2LL/A ")
    @NotBlank
    private String description;

    @Schema(title = "product price", example = "1099.90")
    @NotNull
    private BigDecimal price;

    @Schema(title = "product categories", example = "[\"laptops\", \"apple\"]")
    private Set<String> categories;
}
