package com.ivanfranchin.productui.client.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Set<String> categories;
}
