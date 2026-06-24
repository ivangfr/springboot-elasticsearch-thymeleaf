package com.ivanfranchin.productapi.product.dto;

import com.ivanfranchin.productapi.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public record CreateProductRequest(
    @Schema(title = "product name", example = "Apple 13\" MacBook Pro") @NotBlank String name,
    @Schema(
            title = "product description",
            example =
                "Apple 13\" MacBook Pro, Retina Display, 2.3GHz Intel Core i5 Dual Core, 8GB RAM, 128GB SSD, Space Gray, MPXQ2LL/A ")
        @NotBlank String description,
    @Schema(title = "product price", example = "1099.90") @NotNull BigDecimal price,
    @Schema(title = "product categories", example = "[\"laptops\", \"apple\"]")
        Set<String> categories) {

  public Product toDomain() {
    Product product = new Product();
    product.setName(this.name());
    product.setDescription(this.description());
    product.setPrice(this.price());
    Set<String> set = this.categories();
    if (set != null) {
      product.setCategories(new LinkedHashSet<>(set));
    }
    return product;
  }
}
