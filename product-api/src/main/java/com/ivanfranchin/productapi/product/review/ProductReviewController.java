package com.ivanfranchin.productapi.product.review;

import com.ivanfranchin.productapi.product.ProductService;
import com.ivanfranchin.productapi.product.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products/{id}/reviews")
public class ProductReviewController {

    private final ProductService productService;

    @Operation(summary = "Get reviews about product")
    @GetMapping
    public List<Review> getProductReviews(@PathVariable String id) {
        Product product = productService.validateAndGetProductById(id);
        return product.getReviews();
    }

    @Operation(summary = "Add review about product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Review addProductReview(@PathVariable String id, @Valid @RequestBody AddReviewRequest addReviewRequest) {
        Product product = productService.validateAndGetProductById(id);

        Review review = Review.from(addReviewRequest);
        product.getReviews().add(review);
        productService.saveProduct(product);

        return review;
    }
}
