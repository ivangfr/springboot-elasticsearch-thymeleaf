package com.mycompany.productapi.model;

import com.mycompany.productapi.util.DateTimeUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
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

}
