package com.mycompany.productui.controller;

import com.mycompany.productui.client.ProductApiClient;
import com.mycompany.productui.client.dto.ProductDto;
import com.mycompany.productui.client.dto.Review;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ProductReviewController {

    private final ProductApiClient productApiClient;

    public ProductReviewController(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
    }

    @PostMapping("/products/{id}/review")
    public String addReview(@PathVariable String id, @ModelAttribute Review review) {
        productApiClient.addProductReview(id, review);
        return String.format("redirect:/products/%s/view", id);
    }

}
