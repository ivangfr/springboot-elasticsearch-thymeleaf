package com.mycompany.productui.client.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class Product {

    private String id;
    private String reference;
    private String name;
    private String description;
    private BigDecimal price;
    private Set<String> categories;
    private List<Comment> comments = new ArrayList<>();

}
