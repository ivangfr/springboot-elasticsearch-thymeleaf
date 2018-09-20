package com.mycompany.productapi.controller;

import com.mycompany.productapi.dto.AddReviewDto;
import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Review;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ma.glasnost.orika.MapperFacade;
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

@RestController
@RequestMapping("/api/products/{id}/reviews")
public class ProductReviewController {

    private final ProductService productService;
    private final MapperFacade mapperFacade;

    public ProductReviewController(ProductService productService, MapperFacade mapperFacade) {
        this.productService = productService;
        this.mapperFacade = mapperFacade;
    }

    @ApiOperation(value = "Get reviews about product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Review> getProductReviews(@PathVariable String id) throws ProductNotFoundException {
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review addProductReview(@PathVariable String id, @Valid @RequestBody AddReviewDto addReviewDto)
            throws ProductNotFoundException {
        Product product = productService.validateAndGetProductById(id);

        Review review = mapperFacade.map(addReviewDto, Review.class);
        product.getReviews().add(review);
        productService.saveProduct(product);

        return review;
    }

}
