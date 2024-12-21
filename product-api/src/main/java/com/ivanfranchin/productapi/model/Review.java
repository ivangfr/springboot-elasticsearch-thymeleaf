package com.ivanfranchin.productapi.model;

import com.ivanfranchin.productapi.rest.dto.AddReviewRequest;
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

    public static Review from(AddReviewRequest addReviewRequest) {
        Review review = new Review();
        review.setComment(addReviewRequest.comment());
        if (addReviewRequest.stars() != null) {
            review.setStars(addReviewRequest.stars().shortValue());
        }
        return review;
    }
}
