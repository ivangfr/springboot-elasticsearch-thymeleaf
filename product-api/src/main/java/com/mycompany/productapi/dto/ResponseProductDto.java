package com.mycompany.productapi.dto;

import com.mycompany.productapi.model.Comment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
public class ResponseProductDto {

    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private Set<String> categories;

    private List<Comment> comments;

    private String reference;

}
