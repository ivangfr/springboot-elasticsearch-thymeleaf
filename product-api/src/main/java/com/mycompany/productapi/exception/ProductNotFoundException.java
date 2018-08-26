package com.mycompany.productapi.exception;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(String productId) {
        super(String.format("Product id '%s' not found", productId));
    }

}
