package com.mycompany.productapi.model;

import com.mycompany.productapi.util.DateTimeUtil;
import lombok.Data;

@Data
public class Review {

    public Review() {
        this.created = DateTimeUtil.createCurrentDateAsString();
    }

    private String comment;
    private Short stars;
    private String created;
}
