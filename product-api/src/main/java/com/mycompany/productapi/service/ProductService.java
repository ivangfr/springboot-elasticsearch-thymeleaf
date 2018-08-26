package com.mycompany.productapi.service;

import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    Product validateAndGetProductById(String id) throws ProductNotFoundException;

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    Page<Product> search(String text);

}
