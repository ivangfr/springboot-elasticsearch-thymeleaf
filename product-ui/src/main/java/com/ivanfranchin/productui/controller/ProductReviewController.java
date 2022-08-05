package com.ivanfranchin.productui.controller;

import com.ivanfranchin.productui.client.dto.Review;
import com.ivanfranchin.productui.client.ProductApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ProductReviewController {

    private final ProductApiClient productApiClient;

    @PostMapping("/products/{id}/review")
    public String addReview(@PathVariable String id, @ModelAttribute Review review) {
        productApiClient.addProductReview(id, review);
        return String.format("redirect:/products/%s/view", id);
    }
}
