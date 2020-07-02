package com.mycompany.productapi.rest;

import com.mycompany.productapi.mapper.ProductMapper;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.model.Review;
import com.mycompany.productapi.rest.dto.AddReviewDto;
import com.mycompany.productapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products/{id}/reviews")
public class ProductReviewController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Operation(summary = "Get reviews about product")
    @GetMapping
    public List<Review> getProductReviews(@PathVariable String id) {
        Product product = productService.validateAndGetProductById(id);
        return product.getReviews();
    }

    @Operation(summary = "Add review about product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Review addProductReview(@PathVariable String id, @Valid @RequestBody AddReviewDto addReviewDto) {
        Product product = productService.validateAndGetProductById(id);

        Review review = productMapper.toReview(addReviewDto);
        product.getReviews().add(review);
        productService.saveProduct(product);

        return review;
    }

}
