package com.mycompany.productapi.rest;

import com.mycompany.productapi.rest.dto.CreateProductDto;
import com.mycompany.productapi.rest.dto.SearchDto;
import com.mycompany.productapi.rest.dto.UpdateProductDto;
import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Product;
import com.mycompany.productapi.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ma.glasnost.orika.MapperFacade;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final MapperFacade mapperFacade;

    public ProductController(ProductService productService, MapperFacade mapperFacade) {
        this.productService = productService;
        this.mapperFacade = mapperFacade;
    }

    @ApiOperation(
            value = "Get Products",
            notes = "To sort the results by a specified field, use in 'sort' field a string like: fieldname,[asc|desc]")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public Page<Product> getProducts(Pageable pageable) {
        return productService.listProductsByPage(pageable);
    }

    @ApiOperation(value = "Get Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable String id) throws ProductNotFoundException {
        return productService.validateAndGetProductById(id);
    }

    @ApiOperation(value = "Create Product", code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Product createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        Product product = mapperFacade.map(createProductDto, Product.class);
        return productService.saveProduct(product);
    }

    @ApiOperation(value = "Update Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable String id, @Valid @RequestBody UpdateProductDto updateProductDto)
            throws ProductNotFoundException {
        Product product = productService.validateAndGetProductById(id);
        mapperFacade.map(updateProductDto, product);
        return productService.saveProduct(product);
    }

    @ApiOperation(value = "Delete Product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) throws ProductNotFoundException {
        Product product = productService.validateAndGetProductById(id);
        productService.deleteProduct(product);
        return id;
    }

    @ApiOperation(
            value = "Search for Products",
            notes = "This endpoint queries for a 'text' informed in the following fields: 'reference', 'name' and 'description'\n" +
                    "To sort the results by a specified field, use in 'sort' field a string like: fieldname,[asc|desc]")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/search")
    public Page<Product> searchProducts(@Valid @RequestBody SearchDto searchDto, Pageable pageable) {
        return productService.search(searchDto.getText(), pageable);
    }

}
