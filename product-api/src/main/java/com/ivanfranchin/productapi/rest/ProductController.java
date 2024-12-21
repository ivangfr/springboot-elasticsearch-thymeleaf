package com.ivanfranchin.productapi.rest;

import com.ivanfranchin.productapi.model.Product;
import com.ivanfranchin.productapi.rest.dto.CreateProductRequest;
import com.ivanfranchin.productapi.rest.dto.SearchRequest;
import com.ivanfranchin.productapi.rest.dto.UpdateProductRequest;
import com.ivanfranchin.productapi.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get Products")
    @GetMapping
    public Page<Product> getProducts(@ParameterObject Pageable pageable) {
        return productService.listProductsByPage(pageable);
    }

    @Operation(summary = "Get Product")
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) {
        return productService.validateAndGetProductById(id);
    }

    @Operation(summary = "Create Product")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = Product.from(createProductRequest);
        return productService.saveProduct(product);
    }

    @Operation(summary = "Update Product")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        Product product = productService.validateAndGetProductById(id);
        Product.updateFrom(updateProductRequest, product);
        return productService.saveProduct(product);
    }

    @Operation(summary = "Delete Product")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        Product product = productService.validateAndGetProductById(id);
        productService.deleteProduct(product);
        return id;
    }

    @Operation(
            summary = "Search for Products",
            description = "This endpoint queries for a 'text' informed in the following fields: 'reference', 'name' and 'description'")
    @PutMapping("/search")
    public Page<Product> searchProducts(@Valid @RequestBody SearchRequest searchRequest, @ParameterObject Pageable pageable) {
        return productService.search(searchRequest.text(), pageable);
    }
}
