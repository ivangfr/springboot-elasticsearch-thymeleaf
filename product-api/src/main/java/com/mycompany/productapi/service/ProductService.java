package com.mycompany.productapi.service;

import com.mycompany.productapi.exception.ProductNotFoundException;
import com.mycompany.productapi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> listAllProductsByPage(Pageable pageable);

    Product validateAndGetProductById(String id) throws ProductNotFoundException;

    Product saveProduct(Product product);

    void deleteProduct(Product product);

    Page<Product> search(String text, Pageable pageable);

}
