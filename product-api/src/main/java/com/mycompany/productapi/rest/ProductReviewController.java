package com.mycompany.productapi.rest;

import com.mycompany.productapi.mapper.ProductMapper;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.model.Review;
import com.mycompany.productapi.rest.dto.AddReviewDto;
import com.mycompany.productapi.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "Get reviews about product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public List<Review> getProductReviews(@PathVariable String id) {
        Product product = productService.validateAndGetProductById(id);
        return product.getReviews();
    }

    @ApiOperation(value = "Add review about product", code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
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
