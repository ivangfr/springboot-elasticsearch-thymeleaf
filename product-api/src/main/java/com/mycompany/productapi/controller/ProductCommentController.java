package com.mycompany.productapi.controller;

import com.mycompany.productapi.dto.AddCommentDto;
import com.mycompany.productapi.dto.ResponseCommentDto;
import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Comment;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products/{productId}/comments")
public class ProductCommentController {

    private final ProductService productService;
    private final MapperFacade mapperFacade;

    public ProductCommentController(ProductService productService, MapperFacade mapperFacade) {
        this.productService = productService;
        this.mapperFacade = mapperFacade;
    }

    @ApiOperation(value = "Get comments about the product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCommentDto> getProductComments(@PathVariable String productId) throws ProductNotFoundException {
        Product product = productService.validateAndGetProductById(productId);
        return product.getComments().stream()
                .map(c -> mapperFacade.map(c, ResponseCommentDto.class)).collect(Collectors.toList());
    }

    @ApiOperation(value = "Add comment about the product", code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCommentDto addProductComment(@PathVariable String productId, @Valid @RequestBody AddCommentDto addCommentDto)
            throws ProductNotFoundException {
        Product product = productService.validateAndGetProductById(productId);

        Comment comment = mapperFacade.map(addCommentDto, Comment.class);
        product.getComments().add(comment);
        productService.saveProduct(product);

        return mapperFacade.map(comment, ResponseCommentDto.class);
    }

}
