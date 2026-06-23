package com.ivanfranchin.productui.controller;

import com.ivanfranchin.productui.client.dto.Review;
import com.ivanfranchin.productui.client.ProductApiClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ProductReviewController {

    private final ProductApiClient productApiClient;

    @PostMapping("/products/{id}/review")
    public String addReview(@PathVariable String id, @Valid @ModelAttribute Review review, BindingResult result) {
        if (result.hasErrors()) {
            return String.format("redirect:/products/%s/view", id);
        }
        productApiClient.addProductReview(id, review);
        return String.format("redirect:/products/%s/view", id);
    }
}
