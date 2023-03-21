package com.ivanfranchin.productui.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String name;
    private String description;
    private BigDecimal price;
    private Set<String> categories;
}
