package com.ivanfranchin.productapi.model;

import com.ivanfranchin.productapi.util.DateTimeUtil;
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
