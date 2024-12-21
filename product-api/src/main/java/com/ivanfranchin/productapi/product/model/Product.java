package com.ivanfranchin.productapi.product.model;

import com.ivanfranchin.productapi.product.dto.CreateProductRequest;
import com.ivanfranchin.productapi.product.dto.UpdateProductRequest;
import com.ivanfranchin.productapi.product.review.Review;
import com.ivanfranchin.productapi.util.DateTimeUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(indexName = "ecommerce.products", createIndex = false)
public class Product {

    public Product() {
        this.created = DateTimeUtil.createCurrentDateAsString();
    }

    @Id
    private String id;
    private String reference;
    private String name;
    private String description;
    private BigDecimal price;
    private Set<String> categories;
    private List<Review> reviews = new ArrayList<>();
    private String created;

    public static Product from(CreateProductRequest createProductRequest) {
        Product product = new Product();
        product.setName(createProductRequest.name());
        product.setDescription(createProductRequest.description());
        product.setPrice(createProductRequest.price());
        Set<String> set = createProductRequest.categories();
        if (set != null) {
            product.setCategories(new LinkedHashSet<>(set));
        }
        return product;
    }

    public static void updateFrom(UpdateProductRequest updateProductRequest, Product product) {
        if (updateProductRequest.name() != null) {
            product.setName(updateProductRequest.name());
        }
        if (updateProductRequest.description() != null) {
            product.setDescription(updateProductRequest.description());
        }
        if (updateProductRequest.price() != null) {
            product.setPrice(updateProductRequest.price());
        }
        if (product.getCategories() != null) {
            Set<String> set = updateProductRequest.categories();
            if (set != null) {
                product.getCategories().clear();
                product.getCategories().addAll(set);
            }
        } else {
            Set<String> set = updateProductRequest.categories();
            if (set != null) {
                product.setCategories(new LinkedHashSet<>(set));
            }
        }
    }
}
