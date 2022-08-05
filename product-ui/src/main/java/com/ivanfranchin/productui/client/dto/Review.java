package com.ivanfranchin.productui.client.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Review {

    private String comment;
    private Short stars;
    private Date created;
}
