package com.ivanfranchin.productapi.product.dto;

import com.ivanfranchin.productapi.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public record UpdateProductRequest(
        @Schema(title = "product name", example = "Apple 15\" MacBook Pro") String name,
        @Schema(title = "product description", example = "Apple 15\" MacBook Pro, Retina Display, 2.3GHz Intel Core i5 Dual Core, 8GB RAM, 128GB SSD, Space Gray, MPXQ2LL/A ") String description,
        @Schema(title = "product price", example = "1599.90") BigDecimal price,
        @Schema(title = "product categories", example = "[\"laptops\", \"apple\"]") Set<String> categories) {

    public void update(Product product) {
        if (this.name() != null) {
            product.setName(this.name());
        }
        if (this.description() != null) {
            product.setDescription(this.description());
        }
        if (this.price() != null) {
            product.setPrice(this.price());
        }
        if (product.getCategories() != null) {
            Set<String> set = this.categories();
            if (set != null) {
                product.getCategories().clear();
                product.getCategories().addAll(set);
            }
        } else {
            Set<String> set = this.categories();
            if (set != null) {
                product.setCategories(new LinkedHashSet<>(set));
            }
        }
    }
}
