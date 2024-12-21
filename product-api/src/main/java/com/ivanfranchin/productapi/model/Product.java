package com.ivanfranchin.productapi.model;

import com.ivanfranchin.productapi.rest.dto.CreateProductRequest;
import com.ivanfranchin.productapi.rest.dto.UpdateProductRequest;
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
        product.setName(createProductRequest.getName());
        product.setDescription(createProductRequest.getDescription());
        product.setPrice(createProductRequest.getPrice());
        Set<String> set = createProductRequest.getCategories();
        if (set != null) {
            product.setCategories(new LinkedHashSet<>(set));
        }
        return product;
    }

    public static void updateFrom(UpdateProductRequest updateProductRequest, Product product) {
        if (updateProductRequest.getName() != null) {
            product.setName(updateProductRequest.getName());
        }
        if (updateProductRequest.getDescription() != null) {
            product.setDescription(updateProductRequest.getDescription());
        }
        if (updateProductRequest.getPrice() != null) {
            product.setPrice(updateProductRequest.getPrice());
        }
        if (product.getCategories() != null) {
            Set<String> set = updateProductRequest.getCategories();
            if (set != null) {
                product.getCategories().clear();
                product.getCategories().addAll(set);
            }
        } else {
            Set<String> set = updateProductRequest.getCategories();
            if (set != null) {
                product.setCategories(new LinkedHashSet<>(set));
            }
        }
    }
}
